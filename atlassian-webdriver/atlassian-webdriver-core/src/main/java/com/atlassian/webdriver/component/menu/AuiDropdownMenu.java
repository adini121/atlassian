package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.element.ElementLocated;
import com.atlassian.webdriver.utils.element.ElementNotLocated;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AuiDropdownMenu<T extends TestedProduct> extends AbstractComponent<T, AuiDropdownMenu<T>> implements DropdownMenu
{

    private WebElement menuItem;


    public AuiDropdownMenu(T testedProduct)
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
        return Check.hasClass("active", menuItem);
    }

    public void open()
    {
        if (!isOpen())
        {
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }

        // Wait until the menu has finished loading items
        getTestedProduct().getDriver().waitUntilElementIsNotLocatedAt(By.className("loading"), menuItem);
        // Wait until the menu item has been injected after it's loaded.
        getTestedProduct().getDriver().waitUntilElementIsLocatedAt(By.tagName("li"), menuItem);


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
            menuItem.findElement(By.cssSelector("a.drop")).click();
        }
    }

}