package com.atlassian.webdriver.pageobjects.element;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of TimedElement based on WebDriver
 */
public class WebDriverTimedElement extends AbstractWebDriverElement implements TimedElement
{
    /**
     * Creates a WebDriverTimedElement within the driver's search context and default timeout.
     * @param locator The locator mechanism to use.
     */
    public WebDriverTimedElement(By locator)
    {
        this(locator, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS);
    }

    /**
     * Creates a WebDriverTimedElement within the driver's search context and given timeout.
     * @param locator The locator mechanism to use.
     * @param timeout Timeout in seconds to use while locating this element
     */
    public WebDriverTimedElement(By locator, int timeout)
    {
        super(locator, timeout);

    }

    /**
     * Creates a WebDriverTimedElement within a given search context and default timeout.
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     */
    public WebDriverTimedElement(By locator, SearchContext searchContext)
    {
        this(locator, searchContext, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS);        
    }

    /**
     * Creates a WebDriverTimedElement within a given search context and timeout.
     * @param locator The locator mechanism to use.
     * @param searchContext The SearchContext to use.
     * @param timeout Timeout in seconds to use while locating this element
     */
    public WebDriverTimedElement(By locator, SearchContext searchContext, int timeout)
    {
        super(locator, searchContext, timeout);
    }

    public TimedQuery<Boolean> isPresent()
    {
        return new WebDriverTimedQuery<Boolean>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, Boolean>(){
                    public Boolean apply(WebDriver webDriver) {
                        return driver.elementExistsAt(locator, searchContext);
                    }
        });
    }

    public TimedQuery<Boolean> isVisible()
    {
        this.waitForWebElement();

        return new WebDriverTimedQuery<Boolean>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, Boolean>(){
                    public Boolean apply(WebDriver webDriver) {
                        return driver.elementIsVisibleAt(locator, searchContext);
                    }
        });
    }

    public TimedQuery<String> attribute(final String name)
    {
        this.waitForWebElement();

        return new WebDriverTimedQuery<String>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, String>(){
                    public String apply(WebDriver webDriver) {
                        return driver.findElement(locator).getAttribute(name);
                    }
        });
    }

    public TimedQuery<String> text()
    {
        this.waitForWebElement();

        return new WebDriverTimedQuery<String>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, String>(){
                    public String apply(WebDriver webDriver) {
                        return searchContext.findElement(locator).getText();
                    }
        });
    }

    public TimedQuery<String> value()
    {
        return attribute("value");
    }
}
