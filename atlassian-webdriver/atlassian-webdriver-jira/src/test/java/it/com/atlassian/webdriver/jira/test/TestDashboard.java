package it.com.atlassian.webdriver.jira.test;

import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.LogoutPage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 
 */
public class TestDashboard
{
    private static final JiraTestedProduct JIRA = TestedProductFactory.create(JiraTestedProduct.class);

    @Test
    public void testDashboard()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());
        assertTrue(dashboard.isLoggedInAsUser(new User("admin", "admin", null)));

        JIRA.getPageNavigator().gotoPage(LogoutPage.class).confirmLogout();
    }

    @Test
    public void testDashboardMenu()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        LicenseDetailsPage licenseDetailsPage = dashboard.getAdminMenu().open().gotoLicenseDetailsPage();

        licenseDetailsPage.getUserMenu().open().logout();
    }

    @Test
    public void testDashboardPageHasHeader()
    {
        DashboardPage dashboardPage = JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        JiraHeader header = dashboardPage.getHeader();
    }

    @Test
    public void testLogoutPageClassWorks()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        dashboard.getUserMenu().open().logout();
    }

}
