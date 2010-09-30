package com.atlassian.webdriver.ng.page;

import com.atlassian.webdriver.ng.Link;
import com.atlassian.webdriver.ng.ProductInstance;
import com.atlassian.webdriver.ng.TestedProduct;
import com.atlassian.webdriver.page.WebDriverPage;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public abstract class AbstractPage extends WebDriverPage
{
    private final TestedProduct testedProduct;
    protected AbstractPage(TestedProduct testedProduct)
    {
        super(testedProduct.getDriver(), testedProduct.getProductInstance().getBaseUrl());
        this.testedProduct = testedProduct;
    }

    public ProductInstance getProductInstance()
    {
        return testedProduct.getProductInstance();
    }

    public <T extends AbstractPage> T gotoPage(Link<T> link)
    {
        return link.activate(testedProduct.getPageFactory());
    }


    public TestedProduct getTestedProduct()
    {
        return testedProduct;
    }
}
