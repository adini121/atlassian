package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link PageElement} that waits for element to be
 * present before executing each actions.
 * 
 */
public class WebDriverElement implements PageElement
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected Timeouts timeouts;

    protected final WebDriverLocatable locatable;
    protected final TimeoutType defaultTimeout;

    static WebElement getWebElement(PageElement element)
    {
        if (!WebDriverElement.class.isInstance(element))
        {
            throw new IllegalStateException("Unknown implementation of PageElement, cannot use to retrieve WebElement");
        }
        return WebDriverElement.class.cast(element).asWebElement();
    }

    /**
     * Creates a WebDriverElement within the driver's search context and default timeout
     * @param locator The locator mechanism to use.
     */
    public WebDriverElement(By locator)
    {
        this(locator, TimeoutType.DEFAULT);
    }

    /**
     * Creates a WebDriverElement within the driver's search context and given timeout type.
     * 
     * @param locator The locator mechanism to use.
     * @param timeoutType default timeout of this element
     */
    public WebDriverElement(By locator, TimeoutType timeoutType)
    {
        this(locator, WebDriverLocators.root(), timeoutType);
    }

    /**
     * Creates a WebDriverElement within a given parent and default timeout.
     *
     * @param locator The locator mechanism to use.
     * @param parent The locatable parent of this element.
     */
    public WebDriverElement(By locator, WebDriverLocatable parent)
    {
        this(locator, parent, TimeoutType.DEFAULT);
    }

    /**
     * Creates a WebDriverElement within a given parent and given timeout type.
     *
     * @param locator The locator mechanism to use.
     * @param parent The locatable parent of this element.
     * @param timeoutType default timeout of this element
     */
    public WebDriverElement(By locator, WebDriverLocatable parent, TimeoutType timeoutType)
    {
        this(WebDriverLocators.nested(locator, parent),timeoutType);
    }

    /**
     * Creates a WebDriverElement with the given locatable and timeout type.
     *
     * @param locatable WebDriverLocatable that that locate this element
     * @param timeoutType default timeout of this element
     */
    public WebDriverElement(WebDriverLocatable locatable, TimeoutType timeoutType)
    {
        this.locatable = checkNotNull(locatable, "locatable");
        this.defaultTimeout = checkNotNull(timeoutType, "timeoutType");
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

    protected WebElement waitForWebElement()
    {
        return (WebElement) locatable.waitUntilLocated(driver, timeoutInSeconds());
    }
    
    public boolean isPresent()
    {
        return locatable.isPresent(driver, timeoutInSeconds());
    }

    public boolean isVisible()
    {
        WebElement element = waitForWebElement();
        return element.isDisplayed();
    }

    public boolean isEnabled()
    {
        return waitForWebElement().isEnabled();
    }

    public boolean isSelected()
    {
        return waitForWebElement().isSelected();
    }

    public boolean hasClass(final String className)
    {
        return Check.hasClass(className, waitForWebElement());
    }

    public String getAttribute(final String name)
    {
        return waitForWebElement().getAttribute(name);
    }

    public boolean hasAttribute(final String name, final String value)
    {
        return value.equals(getAttribute(name));
    }

    public String getText()
    {
        return waitForWebElement().getText();
    }

    public String getTagName()
    {
        return waitForWebElement().getTagName();
    }

    public String getValue()
    {
        return waitForWebElement().getAttribute("value");
    }

    public PageElement click()
    {
        waitForWebElement().click();
        return this;
    }

    public PageElement type(final CharSequence... keysToSend)
    {
        waitForWebElement().sendKeys(keysToSend);
        return this;
    }

    public PageElement select()
    {
        WebElement el = waitForWebElement();
        if (!el.isSelected()) {
            el.click();
        }
        return this;
    }

    public PageElement toggle()
    {
        WebElement el = waitForWebElement();
        el.click();
        return this;
    }

    public PageElement clear()
    {
        waitForWebElement().clear();
        return this;
    }

    public TimedElement timed()
    {
       return pageBinder.bind(WebDriverTimedElement.class, locatable, defaultTimeout);
    }

    public PageElementJavascript javascript()
    {
        return new WebDriverElementJavascript(this);
    }

    public PageElement find(By locator)
    {
        return pageBinder.bind(WebDriverElement.class, locator, locatable);
    }

    public PageElement find(By locator, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElement.class, locator, locatable, timeoutType);
    }

    public <T extends PageElement> T find(By locator, Class<T> elementClass)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), locator, locatable);
    }

    public <T extends PageElement> T find(By locator, Class<T> elementClass, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), locator, locatable, timeoutType);
    }

    public List<PageElement> findAll(final By locator)
    {
        return findAll(locator, defaultTimeout);
    }

    public List<PageElement> findAll(By locator, TimeoutType timeoutType)
    {
        return findAll(locator, PageElement.class, timeoutType);
    }

    public <T extends PageElement> List<T> findAll(By locator, Class<T> elementClass)
    {
        return findAll(locator, elementClass, defaultTimeout);
    }

    public <T extends PageElement> List<T> findAll(By locator, Class<T> elementClass, TimeoutType timeoutType)
    {
        List<T> elements = Lists.newLinkedList();
        List<WebElement> webElements = waitForWebElement().findElements(locator);

        for(int i = 0; i < webElements.size(); i++)
        {
            elements.add(pageBinder.bind(WebDriverElementMappings.findMapping(elementClass),
                    WebDriverLocators.list(webElements.get(i), locator, i, locatable), timeoutType));
        }
        return elements;
    }

    /**
     * This allows retreiving the webelement from the page element
     * @return the web element that represents the page element.
     */
    public WebElement asWebElement()
    {
        return waitForWebElement();
    }

    public PageElement withTimeout(TimeoutType timeoutType)
    {
        if (this.defaultTimeout == timeoutType)
        {
            return this;
        }
        return pageBinder.bind(WebDriverElement.class, locatable, checkNotNull(timeoutType));
    }

    @Override
    public String toString()
    {
        return "WebDriverElement[locatable=" + locatable + ",defaultTimeout=" + defaultTimeout + "]";
    }
}
