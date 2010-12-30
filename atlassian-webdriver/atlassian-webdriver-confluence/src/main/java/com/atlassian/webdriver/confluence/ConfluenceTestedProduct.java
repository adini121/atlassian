package com.atlassian.webdriver.confluence;

import com.atlassian.pageobjects.Defaults;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.component.Header;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.confluence.component.header.ConfluenceHeader;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.ConfluenceLoginPage;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.pageobjects.WebDriverPageBinder;
import com.atlassian.webdriver.pageobjects.WebDriverTester;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "confluence", contextPath = "/confluence", httpPort = 1990)
public class ConfluenceTestedProduct implements TestedProduct<WebDriverTester>
{
    private String loggedInUsername;
    private String loggedInPassword;

    private final PageBinder pageBinder;
    private final WebDriverTester webDriverTester;
    private final ProductInstance productInstance;

    public ConfluenceTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
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

        this.pageBinder.override(Header.class, ConfluenceHeader.class);
        this.pageBinder.override(HomePage.class, DashboardPage.class);
        this.pageBinder.override(LoginPage.class, ConfluenceLoginPage.class);
        this.pageBinder.override(AdminHomePage.class, ConfluenceAdminHomePage.class);
    }

    public DashboardPage gotoHomePage()
    {
        return pageBinder.navigateToAndBind(DashboardPage.class);
    }

    public ConfluenceAdminHomePage gotoAdminHomePage()
    {
        return pageBinder.navigateToAndBind(ConfluenceAdminHomePage.class);
    }

    public ConfluenceLoginPage gotoLoginPage()
    {
        return pageBinder.navigateToAndBind(ConfluenceLoginPage.class);
    }

    public <P extends Page> P visit(Class<P> pageClass)
    {
        return pageBinder.navigateToAndBind(pageClass);
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

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getLoggedInPassword() {
        return loggedInPassword;
    }

    public void setLoggedInUser(String loggedInUsername, String password) {
        this.loggedInUsername = loggedInUsername;
        this.loggedInPassword = password;
    }
}