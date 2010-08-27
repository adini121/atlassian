package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.MouseEvents;
import com.atlassian.webdriver.utils.element.ElementIsVisible;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AjsDropdownMenu extends Menu
{
    private WebElement menuItem;

    public AjsDropdownMenu(By by, WebDriver driver)
    {
        this(driver.findElement(by), driver);
    }

    public AjsDropdownMenu(WebElement menuItem, WebDriver driver)
    {
        super(driver);

        this.menuItem = menuItem;
    }

    private boolean isOpen()
    {
        return Check.hasClass("opened", menuItem);
    }

    private AjsDropdownMenu open()
    {
        if (!isOpen())
        {
            MouseEvents.hover(menuItem, getDriver());
        }

        // Wait until the menu has finished loading items
        AtlassianWebDriver.waitUntil(new ElementIsVisible(By.className("ajs-drop-down"), menuItem));

        return this;

    }

    public void activate(String itemId)
    {
        open();
        menuItem.findElement(By.id(itemId)).click();
    }

    public void close()
    {
        if (isOpen())
        {
            MouseEvents.mouseout(menuItem.findElement(By.cssSelector("a.ajs-menu-title")), getDriver());
        }
    }

}
