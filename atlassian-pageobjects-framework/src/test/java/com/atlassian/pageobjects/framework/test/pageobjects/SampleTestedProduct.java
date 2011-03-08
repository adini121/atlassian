package com.atlassian.pageobjects.framework.test.pageobjects;

import com.atlassian.pageobjects.Defaults;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.binder.StandardModule;
import com.atlassian.pageobjects.framework.ElementModule;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.WebDriverElement;
import com.atlassian.pageobjects.framework.timeout.PropertiesBasedTimeouts;
import com.atlassian.pageobjects.framework.timeout.Timeouts;
import com.atlassian.pageobjects.framework.timeout.TimeoutsModule;
import com.atlassian.webdriver.AtlassianWebDriverModule;
import com.atlassian.webdriver.pageobjects.DefaultWebDriverTester;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.openqa.selenium.By;

import static com.google.common.base.Preconditions.checkNotNull;

@Defaults(instanceId = "testapp", contextPath = "/", httpPort = 5990)
public class SampleTestedProduct  implements TestedProduct<WebDriverTester>
{
    private final PageBinder pageBinder;
    private final WebDriverTester  webDriverTester;
    private final ProductInstance productInstance;

    public SampleTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
    {
        checkNotNull(productInstance);
        this.productInstance = productInstance;
        this.webDriverTester =  new DefaultWebDriverTester();
        Timeouts timeouts = PropertiesBasedTimeouts.fromClassPath("com/atlassian/timeout/pageobjects-timeouts.properties");
        this.pageBinder = new InjectPageBinder(productInstance, webDriverTester, new StandardModule(this),
                new AtlassianWebDriverModule(this), new ElementModule(), new TimeoutsModule(timeouts));
    }

    public Element find(final By by)
    {
        return getPageBinder().bind(WebDriverElement.class, by);
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
