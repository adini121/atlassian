package com.atlassian.pageobjects.framework.test.pageobjects;

import com.atlassian.pageobjects.Defaults;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.webdriver.pageobjects.WebDriverTester;

import static org.mockito.Mockito.mock;

@Defaults(instanceId = "testapp", contextPath = "/", httpPort = 5990)
public class MockTestedProduct implements TestedProduct<WebDriverTester>
{
    private final PageBinder pageBinder;
    private final WebDriverTester  webDriverTester;
    private final ProductInstance productInstance;

    public MockTestedProduct()
    {
        this.productInstance = mock(ProductInstance.class);
        this.webDriverTester =  mock(WebDriverTester.class);
        this.pageBinder = mock(PageBinder.class);
    }

    public <P extends Page> P visit(Class<P> pageClass, Object... args)
    {
        return pageBinder.navigateToAndBind(pageClass, args);
    }

    public PageBinder getPageBinder()
    {
        return pageBinder;
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public WebDriverTester getTester()
    {
        return webDriverTester;
    }
}
