package com.atlassian.webdriver.jira;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.product.Defaults;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.pageobjects.AutoInstallWebDriverTester;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.pageobjects.WebDriverPageNavigator;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.jira.page.JiraAdminHomePage;
import com.atlassian.webdriver.jira.page.JiraLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "jira", contextPath = "/jira", httpPort = 2990)
public class JiraTestedProduct implements TestedProduct<WebDriverTester<AtlassianWebDriver>, DashboardPage, JiraAdminHomePage, JiraLoginPage>
{
    private final WebDriverTester<AtlassianWebDriver> webDriverTester;
    private final ProductInstance productInstance;
    private final PageNavigator pageNavigator;

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
        this.pageNavigator = new WebDriverPageNavigator<AtlassianWebDriver>(this);
    }

    public DashboardPage gotoHomePage()
    {
        return pageNavigator.gotoPage(DashboardPage.class);
    }

    public JiraAdminHomePage gotoAdminHomePage()
    {
        return pageNavigator.gotoPage(JiraAdminHomePage.class);
    }

    public JiraLoginPage gotoLoginPage()
    {
        return pageNavigator.gotoPage(JiraLoginPage.class);
    }

    public PageNavigator getPageNavigator()
    {
        return pageNavigator;
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
