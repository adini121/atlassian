package it.com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.*;
import com.atlassian.pageobjects.component.Header;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.WebDriverPageBinder;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import it.com.atlassian.webdriver.pageobjects.components.RefappHeader;
import it.com.atlassian.webdriver.pageobjects.page.RefappAdminHomePage;
import it.com.atlassian.webdriver.pageobjects.page.RefappHomePage;
import it.com.atlassian.webdriver.pageobjects.page.RefappLoginPage;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "refapp", contextPath = "/refapp", httpPort = 5990)
public class RefappTestedProduct implements TestedProduct<WebDriverTester>
{
    private final PageBinder pageBinder;
    private final WebDriverTester webDriverTester;
    private final ProductInstance productInstance;

    public RefappTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
    {
        checkNotNull(productInstance);
        WebDriverTester tester = null;
        if (testerFactory == null)
        {
            tester = new WebDriverTester();
        }
        else
        {
            tester = testerFactory.create();
        }
        this.webDriverTester = tester;
        this.productInstance = productInstance;
        this.pageBinder = new WebDriverPageBinder<AtlassianWebDriver>(this);

        this.pageBinder.override(Header.class, RefappHeader.class);
        this.pageBinder.override(HomePage.class, RefappHomePage.class);
        this.pageBinder.override(AdminHomePage.class, RefappAdminHomePage.class);
        this.pageBinder.override(LoginPage.class, RefappLoginPage.class);
    }

    public RefappHomePage gotoHomePage()
    {
        return pageBinder.navigateToAndBind(RefappHomePage.class);
    }

    public RefappAdminHomePage gotoAdminHomePage()
    {
        return pageBinder.navigateToAndBind(RefappAdminHomePage.class);
    }

    public RefappLoginPage gotoLoginPage()
    {
        return pageBinder.navigateToAndBind(RefappLoginPage.class);
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
