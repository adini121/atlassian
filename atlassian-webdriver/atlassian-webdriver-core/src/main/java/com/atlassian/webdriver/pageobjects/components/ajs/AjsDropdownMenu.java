package com.atlassian.webdriver.pageobjects.components.ajs;

import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.components.DropdownMenu;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.MouseEvents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;

/**
 *
 */
public class AjsDropdownMenu implements DropdownMenu<AjsDropdownMenu>
{

    @Inject
    protected AtlassianWebDriver driver;
    protected WebElement menuItem;

    private final By componentLocator;


    public AjsDropdownMenu(By componentLocator)
    {
        this.componentLocator = componentLocator;
    }

    @Init
    public void initialise()
    {
        menuItem = driver.findElement(componentLocator);
    }

    public boolean isOpen()
    {
        return Check.hasClass("opened", menuItem);
    }

    public <T extends Page> T activate(WebDriverLink<T> link) {
        return link.activate(menuItem);
    }

    public AjsDropdownMenu open()
    {
        if (!isOpen())
        {
            MouseEvents.hover(menuItem, driver);
        }

        // Wait until the menu has finished loading items
        driver.waitUntilElementIsVisibleAt(By.className("ajs-drop-down"), menuItem);
        return this;
    }

    public AjsDropdownMenu close()
    {
        if (isOpen())
        {
            MouseEvents.mouseout(menuItem.findElement(By.cssSelector("a.ajs-menu-title")), driver);
        }
        return this;
    }

}
