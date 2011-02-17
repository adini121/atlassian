package com.atlassian.pageobjects.framework.element;

import com.atlassian.pageobjects.framework.query.TimedQuery;
import com.atlassian.pageobjects.framework.query.webdriver.WebDriverQueryFactory;
import com.atlassian.pageobjects.framework.timeout.TimeoutType;
import org.openqa.selenium.By;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of TimedElement based on WebDriver
 */
public class WebDriverTimedElement implements TimedElement
{
    @Inject
    private WebDriverQueryFactory queryFactory;
    private final By locator;
    private final TimeoutType defaultTimeout;

    /**
     * Create a WebDriverTimedElement with the given timeout
     * @param defaultTimeout default timeout of this element
     * @param locator The locator mechanism to use.
     */
    public WebDriverTimedElement(final By locator, final TimeoutType defaultTimeout)
    {
        this.locator = checkNotNull(locator);
        this.defaultTimeout = checkNotNull(defaultTimeout);
    }
    
    public TimedQuery<Boolean> isPresent()
    {
        return queryFactory.isPresent(locator, defaultTimeout);
    }

    public TimedQuery<Boolean> isVisible()
    {
        return queryFactory.isVisible(locator, defaultTimeout);
    }

    public TimedQuery<Boolean> hasClass(final String className)
    {
        return queryFactory.hasClass(locator, className, defaultTimeout);
    }

    public TimedQuery<String> attribute(final String name)
    {
        return queryFactory.getAttribute(locator, name, defaultTimeout);
    }

    public TimedQuery<String> text()
    {
        return queryFactory.getText(locator, defaultTimeout);
    }

    public TimedQuery<String> value()
    {
        return queryFactory.getValue(locator, defaultTimeout);
    }
}
