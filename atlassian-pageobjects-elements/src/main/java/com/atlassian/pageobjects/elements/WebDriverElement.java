package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

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
    private final TimeoutType defaultTimeout;

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
        this.locatable = WebDriverLocators.single(locator, parent);
        this.defaultTimeout = checkNotNull(timeoutType);
    }

    /**
     * Creates a WebDriverElement with the given locatable and timeout type.
     *
     * @param locatable WebDriverLocatable that that locate this element
     * @param timeoutType default timeout of this element
     */
    public WebDriverElement(WebDriverLocatable locatable, TimeoutType timeoutType)
    {
        this.locatable = locatable;
        this.defaultTimeout = checkNotNull(timeoutType);
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
        return waitForWebElement().getValue();
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
        waitForWebElement().setSelected();
        return this;
    }

    public PageElement toggle()
    {
        waitForWebElement().toggle();
        return this;
    }

    public PageElement clear()
    {
        waitForWebElement().clear();
        return this;
    }

    public TimedElement timed()
    {
       return pageBinder.bind(WebDriverTimedElement.class, locatable.getLocator(),
               locatable.getParent().waitUntilLocated(driver, timeoutInSeconds()), defaultTimeout);
    }

    public WebDriverMouseEvents mouseEvents()
    {
        return new WebDriverMouseEvents(driver, waitForWebElement());
    }

    public List<PageElement> findAll(final By locator)
    {
        List<PageElement> elements = new LinkedList<PageElement>();
        List<WebElement> webElements = waitForWebElement().findElements(locator);

        for(int i = 0; i < webElements.size(); i++)
        {
            elements.add(pageBinder.bind(WebDriverElement.class,
                    WebDriverLocators.list(webElements.get(i), locator, i, locatable), defaultTimeout));
        }
        
        return elements;
    }

    public PageElement find(final By locator)
    {
        return pageBinder.bind(WebDriverElement.class, locator, locatable);
    }
}
