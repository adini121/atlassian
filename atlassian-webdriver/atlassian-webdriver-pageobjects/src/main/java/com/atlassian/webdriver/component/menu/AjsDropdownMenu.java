package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.MouseEvents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *
 */
public class AjsDropdownMenu<T extends TestedProduct> extends AbstractComponent<T, AjsDropdownMenu<T>>
        implements DropdownMenu
{
    protected WebElement menuItem;

    public AjsDropdownMenu(T testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise(final By componentLocator)
    {
        super.initialise(componentLocator);
        this.menuItem = getDriver().findElement(componentLocator);
    }

    public boolean isOpen()
    {
        return Check.hasClass("opened", menuItem);
    }

    public void open()
    {
        if (!isOpen())
        {
            MouseEvents.hover(menuItem, getTestedProduct().getDriver());
        }

        // Wait until the menu has finished loading items
        getTestedProduct().getDriver().waitUntilElementIsVisibleAt(By.className("ajs-drop-down"), menuItem);

    }

    public <T extends PageObject> T activate(Link<T> link)
    {
        open();
        return link.activate(menuItem, getTestedProduct());
    }

    public void close()
    {
        if (isOpen())
        {
            MouseEvents.mouseout(menuItem.findElement(By.cssSelector("a.ajs-menu-title")), getTestedProduct().getDriver());
        }
    }

}
