package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;

import static org.mockito.Mockito.mock;

/**
*
*/
class MyTestedProduct implements TestedProduct<MapTester, MyHeader, HomePage<MyHeader>, AdminHomePage<MyHeader>, LoginPage>
{
    private final MapTester mapTester;
    private final PageBinder pageBinder = mock(PageBinder.class);
    private final ProductInstance productInstance = mock(ProductInstance.class);

    public MyTestedProduct(MapTester mapTester)
    {
        this.mapTester = mapTester;
    }

    public HomePage<MyHeader> gotoHomePage()
    {
        return null;
    }

    public AdminHomePage<MyHeader> gotoAdminHomePage()
    {
        return null;
    }

    public LoginPage gotoLoginPage()
    {
        return null;
    }

    public PageBinder getPageBinder()
    {
        return pageBinder;
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public MapTester getTester()
    {
        return mapTester;
    }

}
