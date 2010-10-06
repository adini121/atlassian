package com.atlassian.webdriver.product;

import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public abstract class AbstractTestedProduct<H extends HomePage, A extends AdminHomePage, L extends LoginPage> implements TestedProduct<H, A, L>
{
    private final WebDriver webDriver;
    private final ProductInstance productInstance;
    private final PageFactory pageFactory;

    public AbstractTestedProduct(WebDriver webDriver, ProductInstance productInstance)
    {
        this.webDriver = webDriver;
        this.productInstance = productInstance;
        this.pageFactory = new PageFactory(this);
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public WebDriver getDriver()
    {
        return webDriver;
    }

    public PageFactory getPageFactory()
    {
        return pageFactory;
    }
}