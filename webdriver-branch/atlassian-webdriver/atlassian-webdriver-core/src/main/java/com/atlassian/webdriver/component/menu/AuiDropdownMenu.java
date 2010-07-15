package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.element.ElementNotLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AuiDropdownMenu extends Menu
{

    private WebElement menuItem;


    public AuiDropdownMenu(By by, WebDriver driver)
    {
        this(driver.findElement(by), driver);
    }

    public AuiDropdownMenu(WebElement menuItem, WebDriver driver)
    {
        super(driver);

        this.menuItem = menuItem;
    }

    private boolean isOpen()
    {
        return Check.hasClass("active", menuItem);
    }

    private AuiDropdownMenu open()
    {
        if (!isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }

        // Wait until the menu has finished loading items
        AtlassianWebDriver.waitUntil(new ElementNotLocated(By.className("loading"), menuItem));

        return this;

    }

    public void activate(String itemId)
    {
        open();
        menuItem.findElement(By.id(itemId)).click();
    }

    private void close()
    {
        if (isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }
    }

}