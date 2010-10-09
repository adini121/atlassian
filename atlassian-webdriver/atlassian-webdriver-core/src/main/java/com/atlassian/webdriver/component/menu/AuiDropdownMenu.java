package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.element.ElementLocated;
import com.atlassian.webdriver.utils.element.ElementNotLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AuiDropdownMenu<T extends TestedProduct> extends Menu<T>
{

    private WebElement menuItem;


    public AuiDropdownMenu(By by, T testedProduct)
    {
        this(testedProduct.getDriver().findElement(by), testedProduct);
    }

    public AuiDropdownMenu(WebElement menuItem, T testedProduct)
    {
        super(testedProduct);

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
        // Wait until the menu item has been injected after it's loaded.
        AtlassianWebDriver.waitUntil(new ElementLocated(By.tagName("li"), menuItem));

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