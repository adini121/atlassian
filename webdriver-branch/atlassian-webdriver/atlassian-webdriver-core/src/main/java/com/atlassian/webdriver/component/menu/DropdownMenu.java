package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.VisibilityOfElementNotLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class DropdownMenu extends Menu
{

    protected WebElement menuItem;


    public DropdownMenu(By by, WebDriver driver)
    {
        this(driver.findElement(by), driver);
    }

    public DropdownMenu(WebElement menuItem, WebDriver driver)
    {
        super(driver);

        this.menuItem = menuItem;
    }

    protected boolean isOpen()
    {
        return Check.hasClass("active", menuItem);
    }

    protected void open()
    {
        if (!isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }

        // Wait until the menu has finished loading items
        AtlassianWebDriver.waitUntil(new VisibilityOfElementNotLocated(By.className("loading"), menuItem));

    }

    protected void close()
    {
        if (isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }
    }

}