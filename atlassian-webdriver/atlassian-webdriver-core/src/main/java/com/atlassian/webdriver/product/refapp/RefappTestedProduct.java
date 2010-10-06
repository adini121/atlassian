package com.atlassian.webdriver.product.refapp;

import com.atlassian.webdriver.product.refapp.page.RefappAdminHomePage;
import com.atlassian.webdriver.product.refapp.page.RefappHomePage;
import com.atlassian.webdriver.product.refapp.page.RefappLoginPage;
import com.atlassian.webdriver.product.AbstractTestedProduct;
import com.atlassian.webdriver.product.ProductInstance;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public class RefappTestedProduct extends AbstractTestedProduct<RefappHomePage, RefappAdminHomePage, RefappLoginPage>
{
    public RefappTestedProduct(WebDriver webDriver, ProductInstance productInstance)
    {
        super(webDriver, productInstance);
    }

    public RefappHomePage gotoHomePage()
    {
        return null;
    }

    public RefappAdminHomePage gotoAdminHomePage()
    {
        return null;
    }

    public RefappLoginPage gotoLoginPage()
    {
        return null;
    }
}
