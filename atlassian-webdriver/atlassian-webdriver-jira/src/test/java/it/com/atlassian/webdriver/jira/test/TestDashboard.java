package it.com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.LogoutPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.utils.by.ByJquery;
import com.atlassian.webdriver.utils.user.User;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import webdriver.browsers.WebDriverBrowserAutoInstall;

import static org.junit.Assert.assertTrue;

/**
 * 
 */
public class TestDashboard
{
    private static final JiraTestedProduct JIRA = TestedProductFactory.create(JiraTestedProduct.class, "jira",
            WebDriverBrowserAutoInstall.INSTANCE.getDriver());

    @Test
    public void testDashboard()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsAdmin();
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());
        assertTrue(dashboard.isLoggedInAsUser(new User("admin", "admin", null)));

        dashboard.gotoPage(LogoutPage.class).confirmLogout();
    }

    @Test
    public void testDashboardMenu()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsAdmin();
        LicenseDetailsPage licenseDetailsPage = dashboard.getAdminMenu().gotoLicenseDetailsPage();

        licenseDetailsPage.getUserMenu().logout();
    }

    @Test
    public void testDashboardPageHasHeader()
    {
        DashboardPage dashboardPage = JIRA.gotoLoginPage().loginAsAdmin();
        JiraHeader header = dashboardPage.getHeader();
    }

    @Test
    public void testLogoutPageClassWorks()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsAdmin();
        dashboard.gotoPage(LogoutPage.class).confirmLogout();

    }

}
