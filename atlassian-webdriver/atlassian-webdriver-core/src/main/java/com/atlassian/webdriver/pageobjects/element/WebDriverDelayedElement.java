package com.atlassian.webdriver.pageobjects.element;

import com.atlassian.webdriver.utils.element.ElementLocated;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.TimeoutException;

/**
 * Implementation of Element that waits for element to be present before executing each actions.
 */
public class WebDriverDelayedElement extends AbstractWebDriverElement implements Element
{
    public static final int DEFAULT_TIMEOUT_SECONDS = 5;

    /**
     * Creates a WebDriverDelayedElement within the driver's search context and default timeout
     * @param locator The locator mechanism to use.
     */
    public WebDriverDelayedElement(By locator)
    {
        this(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Creates a WebDriverDelayedElement within the driver's search context and given timeout
     * @param locator The locator mechanism to use.
     * @param timeoutInSeconds Timeout in seconds to use while locating this element
     */
    public WebDriverDelayedElement(By locator, int timeoutInSeconds)
    {
        super(locator, timeoutInSeconds);
    }

    /**
     * Creates a WebDriverDelayedElement within a given search context and default timeout
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     */
    public WebDriverDelayedElement(By locator, SearchContext searchContext)
    {
        this(locator, searchContext, DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Creates a WebDriverDelayedElement within a given search context and timeout
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     * @param timeoutInSeconds Timeout in seconds to use while locating this element
     */
    public WebDriverDelayedElement(By locator, SearchContext searchContext, int timeoutInSeconds)
    {
        super(locator, searchContext, timeoutInSeconds);
    }

    private boolean waitForCondition(final Function func)
    {
        try
        {
            driver.waitUntil(func, timeoutInSeconds);
        }
        catch(TimeoutException e)
        {
            return false;
        }

        return true;
    }

    public boolean isPresent()
    {
        return driver.elementExistsAt(locator, searchContext);
    }

    public boolean isVisible()
    {
        waitForWebElement();
        return driver.elementIsVisibleAt(locator, searchContext);
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

    public TimedElement timed()
    {
        return pageBinder.bind(WebDriverTimedElement.class, this.locator);
    }

    public void click()
    {
        waitForWebElement().click();
    }

    public void type(CharSequence... keysToSend)
    {
        waitForWebElement().sendKeys(keysToSend);
    }

    public Element findDelayed(By locator)
    {
        return pageBinder.bind(WebDriverDelayedElement.class, locator, waitForWebElement());
    }
}
