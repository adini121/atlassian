package com.atlassian.webdriver.ng.product;

import com.atlassian.webdriver.ng.AbstractTestedProduct;
import com.atlassian.webdriver.ng.ProductInstance;
import com.atlassian.webdriver.ng.TestedProduct;
import com.atlassian.webdriver.ng.page.AdminHomePage;
import com.atlassian.webdriver.ng.page.HomePage;
import com.atlassian.webdriver.ng.page.LoginPage;
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
