package com.atlassian.webdriver.confluence;

import com.atlassian.pageobjects.PageNavigator;
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
import com.atlassian.webdriver.pageobjects.WebDriverPageNavigator;
import com.atlassian.webdriver.pageobjects.WebDriverTester;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "confluence", contextPath = "/confluence", httpPort = 1990)
public class ConfluenceTestedProduct implements TestedProduct<WebDriverTester<AtlassianWebDriver>, DashboardPage, ConfluenceAdminHomePage, ConfluenceLoginPage>
{
    private User loggedInUser;

    private final PageNavigator pageNavigator;
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
        this.pageNavigator = new WebDriverPageNavigator<AtlassianWebDriver>(this);
    }

    public DashboardPage gotoHomePage()
    {
        return pageNavigator.gotoPage(DashboardPage.class);
    }

    public ConfluenceAdminHomePage gotoAdminHomePage()
    {
        return pageNavigator.gotoPage(ConfluenceAdminHomePage.class);
    }

    public ConfluenceLoginPage gotoLoginPage()
    {
        return pageNavigator.gotoPage(ConfluenceLoginPage.class);
    }

    public PageNavigator getPageNavigator()
    {
        return pageNavigator;
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
