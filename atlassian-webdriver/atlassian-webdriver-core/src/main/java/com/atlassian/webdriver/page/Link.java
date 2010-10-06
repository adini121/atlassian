package com.atlassian.webdriver.page;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.product.PageFactory;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;

/**
 *
 */
public class Link<T extends PageObject>
{
    private final By locator;
    private final Class<T> pageObjectClass;

    public Link(By locator, Class<T> pageObjectClass)
    {
        this.locator = locator;
        this.pageObjectClass = pageObjectClass;
    }

    public T activate(PageFactory pageFactory)
    {

        if (Check.elementExists(locator))
        {
            AtlassianWebDriver.getDriver().findElement(locator).click();

            return (T) pageFactory.create(pageObjectClass);
        }

        throw new ElementNotVisibleException("The link could not be activated By(" + locator + ") failed to find element");

    }
}
