package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.query.webdriver.WebDriverQueryFactory;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of TimedElement based on WebDriver
 */
public class WebDriverTimedElement implements TimedElement
{
    @Inject
    PageBinder pageBinder;

    @Inject
    Timeouts timeouts;
    
    private WebDriverQueryFactory queryFactory;
    private final By locator;
    private final SearchContext searchContext;
    private final TimeoutType defaultTimeout;

    /**
     * Create a WebDriverTimedElement with the given timeout
     * @param defaultTimeout default timeout of this element
     * @param locator The locator mechanism to use.
     * @param searchContext The search context to use.
     */
    public WebDriverTimedElement(final By locator, final SearchContext searchContext, final TimeoutType defaultTimeout)
    {
        this.locator = checkNotNull(locator);
        this.searchContext = checkNotNull(searchContext);
        this.defaultTimeout = checkNotNull(defaultTimeout);
    }

    @Init
    public void initialize()
    {
        queryFactory = pageBinder.bind(WebDriverQueryFactory.class, searchContext, timeouts);
    }
    
    public TimedCondition isPresent()
    {
        return queryFactory.isPresent(locator, defaultTimeout);
    }

    public TimedCondition isVisible()
    {
        return queryFactory.isVisible(locator, defaultTimeout);
    }

    public TimedCondition isEnabled()
    {
        return queryFactory.isEnabled(locator, defaultTimeout);
    }

    public TimedCondition isSelected()
    {
        return queryFactory.isSelected(locator, defaultTimeout);
    }

    public TimedCondition hasClass(final String className)
    {
        return queryFactory.hasClass(locator, className, defaultTimeout);
    }

    public TimedQuery<String> getAttribute(final String name)
    {
        return queryFactory.getAttribute(locator, name, defaultTimeout);
    }

    public TimedQuery<String> getText()
    {
        return queryFactory.getText(locator, defaultTimeout);
    }

    public TimedQuery<String> getValue()
    {
        return queryFactory.getValue(locator, defaultTimeout);
    }
}
