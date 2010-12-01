package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.ConfluenceLoginPage;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.confluence.page.LogoutPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestDashboard
{

    private static final ConfluenceTestedProduct CONFLUENCE = TestedProductFactory.create(ConfluenceTestedProduct.class, "confluence",
            WebDriverBrowserAutoInstall.INSTANCE.getDriver());

    @Test
    public void testDashboard()
    {
        ConfluenceLoginPage login = CONFLUENCE.gotoLoginPage();
        login.loginAsAdmin();
        DashboardPage dashboard = CONFLUENCE.gotoHomePage();
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());
        assertTrue(dashboard.isLoggedInAsUser(new User("admin", "admin", null)));

        CONFLUENCE.gotoPage(LogoutPage.class);
    }

    @Test
    public void testDashboardMenu()
    {
        ConfluenceLoginPage login = CONFLUENCE.gotoLoginPage();
        login.loginAsAdmin();
        DashboardPage dashboard = CONFLUENCE.gotoHomePage();
        ConfluenceAdminHomePage adminPage = dashboard.getBrowseMenu().gotoAdminPage();

        LogoutPage logoutPage = adminPage.getUserMenu().logout();

        assertFalse(logoutPage.isLoggedIn());

    }

}
