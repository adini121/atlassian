package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.MouseEvents;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;

/**
 *
 */
public class AjsDropdownMenu
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
