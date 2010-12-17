package com.atlassian.webdriver.pageobjects.menu;

import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AuiDropdownMenu implements DropdownMenu<AuiDropdownMenu>
{

    @Inject
    protected AtlassianWebDriver driver;

    protected WebElement menuItem;

    private final By componentLocator;

    public AuiDropdownMenu(By componentLocator)
    {
        this.componentLocator = componentLocator;
    }

    @Init
    public void initialise()
    {
        menuItem = driver.findElement(componentLocator);
    }

    public <T extends Page> T activate(WebDriverLink<T> link) {
        return link.activate(menuItem);
    }

    public boolean isOpen()
    {
        return Check.hasClass("active", menuItem);
    }

    public AuiDropdownMenu open()
    {
        if (!isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }

        // Wait until the menu has finished loading items
        driver.waitUntilElementIsNotLocatedAt(By.className("loading"), menuItem);
        // Wait until the menu item has been injected after it's loaded.
        driver.waitUntilElementIsLocatedAt(By.tagName("li"), menuItem);

        return this;
    }

    public AuiDropdownMenu close()
    {
        if (isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }
        return this;
    }

}