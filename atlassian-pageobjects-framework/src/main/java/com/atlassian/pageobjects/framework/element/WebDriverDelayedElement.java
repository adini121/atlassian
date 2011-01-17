package com.atlassian.pageobjects.framework.element;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of Element that waits for element to be present before executing each actions.
 */
public class WebDriverDelayedElement implements Element
{
    public static final int DEFAULT_TIMEOUT_SECONDS = 5;    

    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    protected By locator;
    protected SearchContext searchContext;

    private WebElementHolder webElementHolder;
    private WebElement webElement;
    private boolean webElementLocated = false;

    /**
     * Creates a WebDriverDelayedElement within the driver's search context and default timeout
     * @param locator The locator mechanism to use.
     */
    public WebDriverDelayedElement(By locator)
    {
        this(locator, null);
    }

    /**
     * Creates a WebDriverDelayedElement within a given search context and default timeout
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     */
    public WebDriverDelayedElement(By locator, SearchContext searchContext)
    {
        this.locator = locator;
        this.searchContext = searchContext;
    }

    /**
     * Creates a WebDriverDelayedElement with the given WebElement
     * @param webElement The WebElement to wrap in a delayed element.
     */
    public WebDriverDelayedElement(WebElement webElement)
    {
        this.webElementHolder = new WebElementHolder(webElement);
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
     * Waits until WebElement is present
     * @return The WebElement or throws exception if not found.
     */
    protected WebElement waitForWebElement()
    {
        if(!webElementLocated)
        {
            if(!driver.elementExistsAt(locator, searchContext) && DEFAULT_TIMEOUT_SECONDS > 0)
            {
                driver.waitUntil(new ElementLocated(locator, searchContext), DEFAULT_TIMEOUT_SECONDS);
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

    public void click()
    {
        waitForWebElement().click();
    }

    public void type(CharSequence... keysToSend)
    {
        waitForWebElement().sendKeys(keysToSend);
    }

    public TimedElement timed()
    {
       return pageBinder.bind(WebDriverTimedElement.class, this, DEFAULT_TIMEOUT_SECONDS);
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
