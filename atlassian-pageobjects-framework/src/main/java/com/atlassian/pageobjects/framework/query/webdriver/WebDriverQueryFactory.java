package com.atlassian.pageobjects.framework.query.webdriver;

import com.atlassian.pageobjects.framework.query.TimedCondition;
import com.atlassian.pageobjects.framework.query.TimedQuery;
import com.atlassian.pageobjects.framework.timeout.TimeoutType;
import com.atlassian.pageobjects.framework.timeout.Timeouts;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

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
    private final SearchContext searchContext;
    private final Timeouts timeouts;

    public @Inject WebDriverQueryFactory(SearchContext searchContext, Timeouts timeouts)
    {
        this.searchContext = checkNotNull(searchContext);
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

    public TimedCondition isPresent(By locator, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedCondition(searchContext, locator, WebDriverQueryFunctions.isPresent(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isPresent(By locator)
    {
        return isPresent(locator, TimeoutType.DEFAULT);
    }

    public TimedCondition isVisible(By locator, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedCondition(searchContext, locator, WebDriverQueryFunctions.isVisible(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isVisible(By locator)
    {
        return isVisible(locator, TimeoutType.DEFAULT);
    }

    public TimedCondition isEnabled(By locator, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedCondition(searchContext, locator, WebDriverQueryFunctions.isEnabled(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isEnabled(By locator)
    {
        return isEnabled(locator, TimeoutType.DEFAULT);
    }

    public TimedCondition isSelected(By locator, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedCondition(searchContext, locator, WebDriverQueryFunctions.isSelected(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isSelected(By locator)
    {
        return isSelected(locator);
    }

    public TimedQuery<String> getText(By locator, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedQuery<String>(searchContext, locator, WebDriverQueryFunctions.getText(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getText(By locator)
    {
        return getText(locator, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getValue(By locator, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedQuery<String>(searchContext, locator, WebDriverQueryFunctions.getValue(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getValue(By locator)
    {
        return getValue(locator, TimeoutType.DEFAULT);
    }

    public TimedCondition hasAttribute(By locator, String attributeName, String expectedValue, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedCondition(searchContext, locator, WebDriverQueryFunctions.hasAttribute(attributeName, expectedValue),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition hasAttribute(By locator, String attributeName, String expectedValue)
    {
        return hasAttribute(locator, attributeName, expectedValue, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getAttribute(By locator, String attributeName, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedQuery<String>(searchContext, locator, WebDriverQueryFunctions.getAttribute(attributeName),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getAttribute(By locator, String attributeName)
    {
        return getAttribute(locator, attributeName, TimeoutType.DEFAULT);
    }


    public TimedCondition hasClass(By locator, String className, TimeoutType timeoutType)
    {
        return new WebElementBasedTimedCondition(searchContext, locator, WebDriverQueryFunctions.hasClass(className),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition hasClass(By locator, String className)
    {
        return hasClass(locator, className, TimeoutType.DEFAULT);
    }
}
