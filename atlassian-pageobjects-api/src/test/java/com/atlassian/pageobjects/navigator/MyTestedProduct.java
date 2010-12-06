package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;

import static org.mockito.Mockito.mock;

/**
*
*/
class MyTestedProduct implements TestedProduct<MapTester,Page<MapTester>,Page<MapTester>, LoginPage<MapTester>>
{
    private final MapTester mapTester;
    private final PageNavigator pageNavigator = mock(PageNavigator.class);
    private final ProductInstance productInstance = mock(ProductInstance.class);

    public MyTestedProduct(MapTester mapTester)
    {
        this.mapTester = mapTester;
    }

    public Page<MapTester> gotoHomePage()
    {
        return null;
    }

    public Page<MapTester> gotoAdminHomePage()
    {
        return null;
    }

    public LoginPage<MapTester> gotoLoginPage()
    {
        return null;
    }

    public PageNavigator getPageNavigator()
    {
        return pageNavigator;
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
