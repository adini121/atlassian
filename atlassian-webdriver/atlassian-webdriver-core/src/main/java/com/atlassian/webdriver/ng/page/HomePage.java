package com.atlassian.webdriver.ng.page;

import com.atlassian.webdriver.ng.ProductInstance;
import com.atlassian.webdriver.ng.TestedProduct;
import com.atlassian.webdriver.page.WebDriverPage;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public abstract class HomePage extends AbstractPage
{
    protected HomePage(TestedProduct testedProduct)
    {
        super(testedProduct);
    }
}
