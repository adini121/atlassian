package it.com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.LogoutPage;
import com.atlassian.webdriver.jira.page.ViewAttachmentsSettingsPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.junit.Test;
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

}
