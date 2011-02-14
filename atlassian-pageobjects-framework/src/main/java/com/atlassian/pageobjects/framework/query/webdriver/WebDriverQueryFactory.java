package com.atlassian.pageobjects.framework.query.webdriver;

import com.atlassian.pageobjects.framework.query.TimedQuery;
import com.atlassian.pageobjects.framework.timeout.TimeoutType;
import com.atlassian.pageobjects.framework.timeout.Timeouts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.annotation.concurrent.NotThreadSafe;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Creates various WebDriver-based queries.
 *
 */
@NotThreadSafe
public class WebDriverQueryFactory
{
    @Inject
    private WebDriver driver;

    @Inject
    private Timeouts timeouts;

    public WebDriverQueryFactory() {}

    public WebDriverQueryFactory(WebDriver webDriver, Timeouts timeouts)
    {
        this.driver = checkNotNull(webDriver);
        this.timeouts = checkNotNull(timeouts);
    }

    private long interval = -1L;

    private long interval()
    {
        if (interval == -1)
        {
            interval = timeouts.timeoutFor(TimeoutType.EVALUATION_INTERVAL);
            checkState(interval > 0);
        }
        return interval;
    }

    public TimedQuery<Boolean> isPresent(By locator, TimeoutType timeout)
    {
        return new GenericWebDriverTimedQuery<Boolean>(WebDriverQueryFunctions.isPresent(driver, locator),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<Boolean> isPresent(By locator)
    {
        return isPresent(locator, TimeoutType.DEFAULT);
    }

    public TimedQuery<Boolean> isVisible(By locator, TimeoutType timeout)
    {
        return new WebElementBasedTimedQuery<Boolean>(driver, locator, WebDriverQueryFunctions.isVisible(),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<Boolean> isVisible(By locator)
    {
        return isVisible(locator, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getText(By locator, TimeoutType timeout)
    {
        return new WebElementBasedTimedQuery<String>(driver, locator, WebDriverQueryFunctions.getText(),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<String> getText(By locator)
    {
        return getText(locator, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getValue(By locator, TimeoutType timeout)
    {
        return new WebElementBasedTimedQuery<String>(driver, locator, WebDriverQueryFunctions.getValue(),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<String> getValue(By locator)
    {
        return getValue(locator, TimeoutType.DEFAULT);
    }

    public TimedQuery<Boolean> hasAttribute(By locator, String attributeName, String expectedValue, TimeoutType timeout)
    {
        return new WebElementBasedTimedQuery<Boolean>(driver, locator, WebDriverQueryFunctions.hasAttribute(attributeName, expectedValue),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<Boolean> hasAttribute(By locator, String attributeName, String expectedValue)
    {
        return hasAttribute(locator, attributeName, expectedValue, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getAttribute(By locator, String attributeName, TimeoutType timeout)
    {
        return new WebElementBasedTimedQuery<String>(driver, locator, WebDriverQueryFunctions.getAttribute(attributeName),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<String> getAttribute(By locator, String attributeName)
    {
        return getAttribute(locator, attributeName, TimeoutType.DEFAULT);
    }


    public TimedQuery<Boolean> hasClass(By locator, String className, TimeoutType timeout)
    {
        return new WebElementBasedTimedQuery<Boolean>(driver, locator, WebDriverQueryFunctions.hasClass(className),
                timeouts.timeoutFor(timeout), interval());
    }

    public TimedQuery<Boolean> hasClass(By locator, String className)
    {
        return hasClass(locator, className, TimeoutType.DEFAULT);
    }
}
