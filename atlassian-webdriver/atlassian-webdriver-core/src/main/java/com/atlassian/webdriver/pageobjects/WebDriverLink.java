package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.Link;
import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.SearchContext;

import javax.inject.Inject;

/**
 *
 */
public abstract class WebDriverLink<P extends Page> implements Link<P>
{
    @Inject
    private AtlassianWebDriver webDriver;

    @Inject
    private PageNavigator pageNavigator;

    private final By locator;
    private final Class<P> pageObjectClass;

    public WebDriverLink(By locator, Class<P> pageObjectClass)
    {
        this.locator = locator;
        this.pageObjectClass = pageObjectClass;
    }

    public P activate()
    {
        return activate(webDriver);
    }

    public P activate(SearchContext context)
    {
        if (webDriver.elementExistsAt(locator, context))
        {
            context.findElement(locator).click();

            return (P) pageNavigator.build(pageObjectClass);
        }

        throw new ElementNotVisibleException("The link could not be activated By(" + locator + ") failed to find element");
    }
}
