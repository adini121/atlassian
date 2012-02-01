package it.com.atlassian.webdriver.jira.test;

import com.atlassian.pageobjects.Browser;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.LogoutPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import com.atlassian.webdriver.testing.rule.IgnoreBrowserRule;
import com.atlassian.webdriver.testing.rule.SessionCleanupRule;
import com.atlassian.webdriver.testing.rule.TestedProductRule;
import com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 
 */
public class TestDashboard
{
    @Rule public IgnoreBrowserRule ignoreRule = new IgnoreBrowserRule();
    @Rule public TestedProductRule<JiraTestedProduct> product =
        new TestedProductRule<JiraTestedProduct>(JiraTestedProduct.class);
    @Rule public WebDriverScreenshotRule webDriverScreenshotRule = new WebDriverScreenshotRule();
    @Rule public SessionCleanupRule sessionCleanupRule = new SessionCleanupRule();

    JiraTestedProduct jira;
    @Before
    public void initJira()
    {
        jira = product.getTestedProduct();
    }

    @Test
    public void testDashboard()
    {
        DashboardPage dashboard = jira.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());
    }

    @Test
    @IgnoreBrowser(value = {Browser.HTMLUNIT_NOJS, Browser.HTMLUNIT}, reason = "Shadows don't work for AUI dropdowns")
    public void testDashboardMenu()
    {
        DashboardPage dashboard = jira.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        LicenseDetailsPage licenseDetailsPage = dashboard.getHeader().getAdminMenu().open().gotoLicenseDetailsPage();
    }

    @Test
    @TestBrowser("htmlunit")
    public void testDashboardMenuBreaksInHtmlUnit()
    {
        DashboardPage dashboard = jira.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        final AdminMenu adminMenu = dashboard.getHeader().getAdminMenu();
        try
        {
            adminMenu.open();
            fail("HtmlUnit broke on rendering the dropdown shadow");
        }
        catch (TimeoutException expected) {}
    }

    @Test
    public void testDashboardAdminMenuExists()
    {
        jira.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        PageBinder binder = product.getPageBinder();

        assertTrue(binder.delayedBind(AdminMenu.class).canBind());
    }

    @Test
    public void testDashboardPageHasHeader()
    {
        DashboardPage dashboardPage = jira.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        JiraHeader header = dashboardPage.getHeader();
    }

    @Test
    public void testLogoutPageClassWorks()
    {
        DashboardPage dashboard = jira.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
    }

    @After
    public void logout()
    {
        if (jira.gotoLoginPage().isLoggedIn())
        {
            product.getPageBinder().navigateToAndBind(LogoutPage.class).confirmLogout();
        }
    }

}
