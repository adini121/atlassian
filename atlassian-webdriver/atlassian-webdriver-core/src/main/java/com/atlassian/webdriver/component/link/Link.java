package com.atlassian.webdriver.component.link;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Link<T extends PageObject>
{

    private final By locator;
    //private final Class<T> pageObjectClass;

    public Link(By locator/*, Class<T> pageObjectClass*/)
    {
        this.locator = locator;
        /*this.pageObjectClass = pageObjectClass;*/
    }

    public T activate(WebDriver driver)
    {

        if (Check.elementExists(locator))
        {
            AtlassianWebDriver.getDriver().findElement(locator).click();

            return (T) PageLinkFactory.get(this).get(driver, true); /*pageObjectClass.cast(PageLinkFactory.get(this).get(driver, true));*/
        }

        throw new ElementNotVisibleException("The link could not be activated By(" + locator + ") failed to find element");

    }

}
