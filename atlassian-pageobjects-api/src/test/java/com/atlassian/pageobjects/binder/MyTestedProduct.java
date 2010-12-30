package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.TestedProductFactory;

import static org.mockito.Mockito.mock;

/**
*
*/
class MyTestedProduct implements TestedProduct<MapTester>
{
    private final MapTester mapTester;
    private final PageBinder pageBinder = mock(PageBinder.class);
    private final ProductInstance productInstance = mock(ProductInstance.class);

    public MyTestedProduct(MapTester mapTester)
    {
        this.mapTester = mapTester;
    }


    public <P extends Page> P visit(Class<P> pageClass, Object... args)
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
