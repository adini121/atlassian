package com.atlassian.webdriver;

import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;

/**
 *
 */
public class Link<T extends PageObject>
{
    private final By locator;
    private final Class<T> pageObjectClass;

    public Link(By locator, Class<T> pageObjectClass)
    {
        this.locator = locator;
        this.pageObjectClass = pageObjectClass;
    }

    public T activate(TestedProduct testedProduct)
    {

        if (Check.elementExists(locator, testedProduct.getDriver()))
        {
            testedProduct.getDriver().findElement(locator).click();

            return (T) testedProduct.gotoPage(pageObjectClass, true);
        }

        throw new ElementNotVisibleException("The link could not be activated By(" + locator + ") failed to find element");

    }
}
