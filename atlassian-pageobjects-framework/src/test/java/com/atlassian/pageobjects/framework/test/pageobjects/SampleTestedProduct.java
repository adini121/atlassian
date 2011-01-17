package com.atlassian.pageobjects.framework.test.pageobjects;

import com.atlassian.pageobjects.*;
import com.atlassian.pageobjects.framework.ElementPageBinder;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.WebDriverDelayedElement;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.openqa.selenium.By;

import static com.google.common.base.Preconditions.checkNotNull;

@Defaults(instanceId = "testapp", contextPath = "/", httpPort = 5990)
public class SampleTestedProduct  implements TestedProduct<WebDriverTester>
{
    private final ElementPageBinder pageBinder;
    private final WebDriverTester  webDriverTester;
    private final ProductInstance productInstance;
    
     public SampleTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
    {
        checkNotNull(productInstance);
        this.productInstance = productInstance;
        this.webDriverTester =  new WebDriverTester();
        this.pageBinder = new ElementPageBinder(this);

        // Add the ElementPageBinder to the list of injectables.
        this.webDriverTester.getInjectables().put(ElementPageBinder.class, this.pageBinder);
    }

    public Element find(final By by)
    {
        return getPageBinder().bind(WebDriverDelayedElement.class, by);
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
