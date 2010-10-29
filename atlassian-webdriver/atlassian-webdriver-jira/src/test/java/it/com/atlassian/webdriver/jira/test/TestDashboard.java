package it.com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 
 */
public class TestDashboard extends AbstractJiraWebDriverTest
{

    @Test
    public void testDashboard()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsAdmin();
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());
        assertTrue(dashboard.isLoggedInAsUser(new User("admin", "admin", null)));
    }

    @Test
    public void testDashboardMenu()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsAdmin();
        LicenseDetailsPage licenseDetailsPage = dashboard.getAdminMenu().gotoLicenseDetailsPage();

        licenseDetailsPage.getUserMenu().logout();
    }

}
