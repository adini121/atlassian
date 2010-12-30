package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.confluence.page.LogoutPage;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestDashboard extends AbstractConfluenceWebTest
{

    @Test
    public void testDashboard()
    {
        DashboardPage dashboard = CONFLUENCE.gotoHomePage();
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());

        pageBinder.navigateToAndBind(LogoutPage.class);
    }

    @Test
    public void testDashboardMenu()
    {
        DashboardPage dashboard = CONFLUENCE.gotoHomePage();
        ConfluenceAdminHomePage adminPage = dashboard.getBrowseMenu().open().gotoAdminPage();

        LogoutPage logoutPage = adminPage.getUserMenu().open().logout();

        assertFalse(logoutPage.isLoggedIn());

    }

}
