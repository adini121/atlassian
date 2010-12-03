package com.atlassian.webdriver;

import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.SearchContext;

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
        return activate(testedProduct.getDriver(), testedProduct);
    }

    public T activate(SearchContext context, TestedProduct testedProduct)
    {
        if (testedProduct.getDriver().elementExistsAt(locator, context))
        {
            context.findElement(locator).click();

            return (T) testedProduct.gotoPage(pageObjectClass, true);
        }

        throw new ElementNotVisibleException("The link could not be activated By(" + locator + ") failed to find element");
    }
}
