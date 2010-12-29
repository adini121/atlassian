package com.atlassian.webdriver.jira;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.DelayedBinder;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.page.Header;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.Defaults;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.pageobjects.WebDriverPageBinder;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.jira.page.JiraAdminHomePage;
import com.atlassian.webdriver.jira.page.JiraLoginPage;
import com.atlassian.webdriver.pageobjects.components.UserMenu;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "jira", contextPath = "/jira", httpPort = 2990)
public class JiraTestedProduct implements TestedProduct<WebDriverTester, JiraHeader, DashboardPage, JiraAdminHomePage, JiraLoginPage>
{
    private final WebDriverTester webDriverTester;
    private final ProductInstance productInstance;
    private final PageBinder pageBinder;

    public JiraTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
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

        this.pageBinder.override(Header.class, JiraHeader.class);
        this.pageBinder.override(HomePage.class, DashboardPage.class);
        this.pageBinder.override(AdminHomePage.class, JiraAdminHomePage.class);
        this.pageBinder.override(LoginPage.class, JiraLoginPage.class);
    }

    public DashboardPage gotoHomePage()
    {
        return pageBinder.navigateToAndBind(DashboardPage.class);
    }

    public JiraAdminHomePage gotoAdminHomePage()
    {
        return pageBinder.navigateToAndBind(JiraAdminHomePage.class);
    }

    public JiraLoginPage gotoLoginPage()
    {
        return pageBinder.navigateToAndBind(JiraLoginPage.class);
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
