package it.com.atlassian.webdriver.jira.test;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.LogoutPage;
import org.junit.After;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestJiraTestedProduct
{
    private static final JiraTestedProduct JIRA = TestedProductFactory.create(JiraTestedProduct.class);

    @Test
    public void testLoginInfo()
    {
        JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        assertTrue(JIRA.isAdmin());
        assertTrue(JIRA.isLoggedIn());
        assertTrue(JIRA.isLoggedInAsUser(new User("admin", "admin", null)));
    }

    @Test
    public void testLogout()
    {
        assertFalse(JIRA.isAdmin());
        assertFalse(JIRA.isLoggedIn());
        assertFalse(JIRA.isLoggedInAsUser(new User("admin", "admin", null)));
    }

    @After
    public void logout()
    {
        if (JIRA.isLoggedIn())
        {
            JIRA.getPageBinder().navigateToAndBind(LogoutPage.class).confirmLogout();
        }
    }
}
