package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.MouseEvents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AjsDropdownMenu<T extends TestedProduct> extends Menu<T>
{
    private WebElement menuItem;

    public AjsDropdownMenu(By by, T testedProduct)
    {
        this(testedProduct.getDriver().findElement(by), testedProduct);
    }

    public AjsDropdownMenu(WebElement menuItem, T testedProduct)
    {
        super(testedProduct);

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
            MouseEvents.hover(menuItem, getTestedProduct().getDriver());
        }

        // Wait until the menu has finished loading items
        getTestedProduct().getDriver().waitUntilElementIsVisibleAt(By.className("ajs-drop-down"), menuItem);

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
            MouseEvents.mouseout(menuItem.findElement(By.cssSelector("a.ajs-menu-title")), getTestedProduct().getDriver());
        }
    }

}
