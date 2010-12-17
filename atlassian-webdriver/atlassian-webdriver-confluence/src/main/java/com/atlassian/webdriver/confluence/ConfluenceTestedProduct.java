package com.atlassian.webdriver.confluence;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.Defaults;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.pageobjects.AutoInstallWebDriverTester;
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
public class ConfluenceTestedProduct implements TestedProduct<WebDriverTester<AtlassianWebDriver>, DashboardPage, ConfluenceAdminHomePage, ConfluenceLoginPage>
{
    private User loggedInUser;

    private final PageBinder pageBinder;
    private final WebDriverTester<AtlassianWebDriver> webDriverTester;
    private final ProductInstance productInstance;

    public ConfluenceTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester<AtlassianWebDriver>> testerFactory, ProductInstance productInstance)
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

    public ConfluenceAdminHomePage gotoAdminHomePage()
    {
        return pageBinder.navigateToAndBind(ConfluenceAdminHomePage.class);
    }

    public ConfluenceLoginPage gotoLoginPage()
    {
        return pageBinder.navigateToAndBind(ConfluenceLoginPage.class);
    }

    public PageBinder getPageBinder()
    {
        return pageBinder;
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public WebDriverTester<AtlassianWebDriver> getTester()
    {
        return webDriverTester;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
