package com.atlassian.pageobjects.framework.element;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.framework.timeout.TimeoutType;
import com.atlassian.pageobjects.framework.timeout.Timeouts;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.hamcrest.StringDescription;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.TimeoutException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link com.atlassian.pageobjects.framework.element.Element} that waits for element to be
 * present before executing each actions.
 * 
 */
public class WebDriverDelayedElement implements Element
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected Timeouts timeouts;

    protected By locator;
    protected SearchContext searchContext;

    private WebElementHolder webElementHolder;
    private WebElement webElement;
    private boolean webElementLocated = false;
    private final TimeoutType defaultTimeout;

    /**
     * Creates a WebDriverDelayedElement within the driver's search context and default timeout
     * @param locator The locator mechanism to use.
     */
    public WebDriverDelayedElement(By locator)
    {
        this(locator, null, TimeoutType.DEFAULT);
    }

    /**
     * Creates a WebDriverDelayedElement within the driver's search context and default timeout.
     * 
     * @param locator The locator mechanism to use.
     * @param defaultTimeout default timeout of this element
     */
    public WebDriverDelayedElement(By locator, TimeoutType defaultTimeout)
    {
        this(locator, null, defaultTimeout);
    }

    /**
     * Creates a WebDriverDelayedElement within a given search context and default timeout.
     *
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     */
    public WebDriverDelayedElement(By locator, SearchContext searchContext)
    {
        this(locator, searchContext, TimeoutType.DEFAULT);
    }

    /**
     * Creates a WebDriverDelayedElement within a given search context and default timeout.
     *
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     * @param defaultTimeout default timeout of this element
     */
    public WebDriverDelayedElement(By locator, SearchContext searchContext, TimeoutType defaultTimeout)
    {
        this.locator = locator;
        this.searchContext = searchContext;
        this.defaultTimeout = checkNotNull(defaultTimeout);
    }

    /**
     * Creates a WebDriverDelayedElement with the given WebElement and default timeout.
     *
     * @param webElement The WebElement to wrap in a delayed element.
     * @param defaultTimeout default timeout of this element
     */
    public WebDriverDelayedElement(WebElement webElement, TimeoutType defaultTimeout)
    {
        this.webElementHolder = new WebElementHolder(webElement);
        this.defaultTimeout = checkNotNull(defaultTimeout);
    }


    /**
     * Creates a WebDriverDelayedElement with the given WebElement.
     *
     * @param webElement The WebElement to wrap in a delayed element.
     */
    public WebDriverDelayedElement(WebElement webElement)
    {
        this(webElement, TimeoutType.DEFAULT);
    }

    protected long timeout()
    {
        return timeouts.timeoutFor(defaultTimeout);
    }

    protected int timeoutInSeconds()
    {
        // sucks sucks sucks sucks sucks....
        return (int) TimeUnit.MILLISECONDS.toSeconds(timeout());
    }

    @Init
    public void initialize()
    {
        if(searchContext == null)
        {
            searchContext = driver;
        }
        if(this.webElementHolder != null)
        {
            this.webElement = this.webElementHolder.getWebElement();
            this.webElementLocated = true;
        }
    }

    /**
     * Class that holds a reference to a WebElement, used so that WebDriverDelayedElement can be instantiated with
     * an exisiting instance of WebElment. Otherwise it would be overriden by the pageinjector.
     */
    private class WebElementHolder
    {
        private final WebElement webElement;

        public WebElementHolder(WebElement webElement)
        {
            this.webElement = webElement;
        }

        public WebElement getWebElement()
        {
            return this.webElement;
        }
    }

    /**
     * Waits until WebElement is present.
     * 
     * @return The WebElement or throws exception if not found.
     */
    protected WebElement waitForWebElement()
    {
        if(!webElementLocated)
        {
            if(!driver.elementExistsAt(locator, searchContext) && timeoutInSeconds() > 0)
            {
                try
                {
                    driver.waitUntil(new ElementLocated(locator, searchContext), timeoutInSeconds());
                }
                catch(TimeoutException e)
                {
                    throw new org.openqa.selenium.NoSuchElementException(new StringDescription()
                            .appendText("Unable to locate element after timeout.")
                            .appendText("\nLocator: ").appendValue(locator)
                            .appendText("\nTimeout: ").appendValue(timeoutInSeconds()).appendText(" seconds.")
                            .toString(), e);
                }
            }

            webElement = searchContext.findElement(locator);
            webElementLocated = true;
        }
        return webElement;
    }
    
    public boolean isPresent()
    {
        // TODO: [Issue #SELENIUM-54] if the element was already located, isPresent() always returns true.
        return webElementLocated || driver.elementExistsAt(locator, searchContext);
    }

    public boolean isVisible()
    {
        WebElement element = waitForWebElement();
        return ((RenderedWebElement) element).isDisplayed();
    }

    public boolean isEnabled()
    {
        return waitForWebElement().isEnabled();
    }

    public boolean isSelected()
    {
        return waitForWebElement().isSelected();
    }

    public boolean hasClass(String className)
    {
        return Check.hasClass(className, waitForWebElement());
    }

    public String attribute(String name)
    {
        return waitForWebElement().getAttribute(name);
    }

    public String text()
    {
        return waitForWebElement().getText();
    }

    public String value()
    {
        return waitForWebElement().getValue();
    }

    public Element click()
    {
        waitForWebElement().click();
        return this;
    }

    public Element type(CharSequence... keysToSend)
    {
        waitForWebElement().sendKeys(keysToSend);
        return this;
    }

    public Element select()
    {
        waitForWebElement().setSelected();
        return this;
    }

    public Element clear()
    {
        waitForWebElement().clear();
        return this;
    }

    public TimedElement timed()
    {
       return pageBinder.bind(WebDriverTimedElement.class, locator, searchContext, defaultTimeout);
    }

    public WebDriverMouseEvents mouseEvents()
    {
        return new WebDriverMouseEvents(driver, waitForWebElement());
    }

    public List<Element> findAll(By locator)
    {
        List<Element> elements = new LinkedList<Element>();
        List<WebElement> webElements = waitForWebElement().findElements(locator);
        for(WebElement e: webElements)
        {
            elements.add(pageBinder.bind(WebDriverDelayedElement.class, e));
        }
        return elements;
    }

    public Element find(By locator)
    {
        return pageBinder.bind(WebDriverDelayedElement.class, locator, waitForWebElement());
    }
}
