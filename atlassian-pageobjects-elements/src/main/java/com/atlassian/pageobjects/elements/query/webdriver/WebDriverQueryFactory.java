package com.atlassian.pageobjects.elements.query.webdriver;

import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;

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
    private final WebDriverLocatable locatable;

    @Inject
    private Timeouts timeouts;

    @Inject
    private AtlassianWebDriver webDriver;

    public WebDriverQueryFactory(WebDriverLocatable locatable)
    {
        this.locatable = checkNotNull(locatable);
    }

    public WebDriverQueryFactory(WebDriverLocatable locatable, Timeouts timeouts, AtlassianWebDriver webDriver)
    {
        this.locatable = checkNotNull(locatable);
        this.timeouts = timeouts;
        this.webDriver = webDriver;
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

    public TimedCondition isPresent(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver, WebDriverQueryFunctions.isPresent(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isPresent()
    {
        return isPresent(TimeoutType.DEFAULT);
    }

    public TimedCondition isVisible(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver, WebDriverQueryFunctions.isVisible(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isVisible()
    {
        return isVisible(TimeoutType.DEFAULT);
    }

    public TimedCondition isEnabled(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver, WebDriverQueryFunctions.isEnabled(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isEnabled()
    {
        return isEnabled(TimeoutType.DEFAULT);
    }

    public TimedCondition isSelected(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver, WebDriverQueryFunctions.isSelected(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition isSelected()
    {
        return isSelected(TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getText(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedQuery<String>(locatable, webDriver, WebDriverQueryFunctions.getText(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getText()
    {
        return getText(TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getValue(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedQuery<String>(locatable, webDriver, WebDriverQueryFunctions.getValue(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getValue()
    {
        return getValue(TimeoutType.DEFAULT);
    }

    public TimedCondition hasAttribute(String attributeName, String expectedValue, TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver,
                WebDriverQueryFunctions.hasAttribute(attributeName, expectedValue),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition hasAttribute(String attributeName, String expectedValue)
    {
        return hasAttribute(attributeName, expectedValue, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getAttribute(String attributeName, TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedQuery<String>(locatable, webDriver,
                WebDriverQueryFunctions.getAttribute(attributeName),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getAttribute(String attributeName)
    {
        return getAttribute(attributeName, TimeoutType.DEFAULT);
    }


    public TimedCondition hasClass(String className, TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver, WebDriverQueryFunctions.hasClass(className),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition hasClass(String className)
    {
        return hasClass(className, TimeoutType.DEFAULT);
    }

    public TimedQuery<String> getTagName(TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedQuery<String>(locatable, webDriver, WebDriverQueryFunctions.getTagName(),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedQuery<String> getTagName()
    {
        return getTagName(TimeoutType.DEFAULT);
    }

    public TimedCondition hasText(String text, TimeoutType timeoutType)
    {
        return new WebDriverLocatableBasedTimedCondition(locatable, webDriver, WebDriverQueryFunctions.hasText(text),
                timeouts.timeoutFor(timeoutType), interval());
    }

    public TimedCondition hasText(By locator, String text)
    {
        return hasText(text, TimeoutType.DEFAULT);
    }
}
