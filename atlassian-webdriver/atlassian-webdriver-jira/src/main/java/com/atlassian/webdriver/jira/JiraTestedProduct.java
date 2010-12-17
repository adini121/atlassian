package com.atlassian.webdriver.jira;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.DelayedBinder;
import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.Defaults;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.pageobjects.AutoInstallWebDriverTester;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.pageobjects.WebDriverPageBinder;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.jira.page.JiraAdminHomePage;
import com.atlassian.webdriver.jira.page.JiraLoginPage;
import com.atlassian.webdriver.pageobjects.menu.UserMenu;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "jira", contextPath = "/jira", httpPort = 2990)
public class JiraTestedProduct implements TestedProduct<WebDriverTester<AtlassianWebDriver>, DashboardPage, JiraAdminHomePage, JiraLoginPage>
{
    private final WebDriverTester<AtlassianWebDriver> webDriverTester;
    private final ProductInstance productInstance;
    private final PageBinder pageBinder;

    public JiraTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester<AtlassianWebDriver>> testerFactory, ProductInstance productInstance)
    {
        checkNotNull(productInstance);
        WebDriverTester<AtlassianWebDriver> tester = null;
        if (testerFactory == null)
        {
            tester = new AutoInstallWebDriverTester();
        }
        else
        {
            tester = testerFactory.create();
        }
        this.webDriverTester = tester;
        this.productInstance = productInstance;
        this.pageBinder = new WebDriverPageBinder<AtlassianWebDriver>(this);
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

    public boolean isLoggedIn()
    {
        DelayedBinder<JiraHeader> header = pageBinder.delayedBind(JiraHeader.class);
        return header.canBind() ? header.bind().isLoggedIn() : false;
    }

    public boolean isLoggedInAsUser(User user)
    {
        DelayedBinder<JiraHeader> header = pageBinder.delayedBind(JiraHeader.class);
        return header.canBind() ? header.bind().isLoggedInAsUser(user) : false;
    }

    public boolean isAdmin()
    {
        DelayedBinder<JiraHeader> header = pageBinder.delayedBind(JiraHeader.class);
        return header.canBind() ? header.bind().isAdmin() : false;
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
