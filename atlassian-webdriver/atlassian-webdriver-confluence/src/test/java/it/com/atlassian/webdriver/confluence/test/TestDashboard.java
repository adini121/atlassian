package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.ConfluenceLoginPage;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestDashboard
{
    @Test
    public void testDashboard()
    {
        ConfluenceTestedProduct conf = TestedProductFactory.create(ConfluenceTestedProduct.class, "confluence", webdriver.browsers.WebdriverBrowserAutoInstall.getDriver());
        ConfluenceLoginPage login = conf.gotoLoginPage();
        login.loginAsAdmin();
        DashboardPage dashboard = conf.gotoHomePage();
        assertTrue(dashboard.isAdmin());
        assertTrue(dashboard.isLoggedIn());
        assertTrue(dashboard.isLoggedInAsUser(new User("admin", "admin", null)));
    }

}
