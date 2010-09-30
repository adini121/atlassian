package com.atlassian.webdriver.ng;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.component.link.PageLinkFactory;
import com.atlassian.webdriver.ng.page.AbstractPage;
import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public class Link<T extends AbstractPage>
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
