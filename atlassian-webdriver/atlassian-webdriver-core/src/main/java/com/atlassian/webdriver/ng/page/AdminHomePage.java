package com.atlassian.webdriver.ng.page;

import com.atlassian.webdriver.ng.ProductInstance;
import com.atlassian.webdriver.ng.TestedProduct;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public abstract class AdminHomePage extends AbstractPage
{
    protected AdminHomePage(TestedProduct testedProduct)
    {
        super(testedProduct);
    }
}
