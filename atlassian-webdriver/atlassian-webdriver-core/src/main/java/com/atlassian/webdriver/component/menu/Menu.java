package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.Linkable;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.product.TestedProduct;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Menu<T extends TestedProduct> implements Linkable
{

    private final T testedProduct;

    public Menu(T testedProduct)
    {
        Validate.notNull(testedProduct, "Tested product cannot be null.");
        this.testedProduct = testedProduct;
    }

    public <T extends PageObject> T gotoPage(Link<T> link) {
        return link.activate(testedProduct);
    }

    public T getTestedProduct() {
        return testedProduct;
    }

    public WebDriver getDriver()
    {
        return getTestedProduct().getDriver();
    }
}