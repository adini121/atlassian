package it.com.atlassian.webdriver.jira.test;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.LogoutPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.utils.Browser;
import org.junit.After;
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
    }

    @Test
    @IgnoreBrowser(Browser.HTMLUNIT_NOJS)
    public void testDashboardMenu()
    {
        DashboardPage dashboard = JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        LicenseDetailsPage licenseDetailsPage = dashboard.getHeader().getAdminMenu().open().gotoLicenseDetailsPage();
    }

    @Test
    public void testDashboardAdminMenuExists()
    {
        JIRA.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        PageBinder binder = JIRA.getPageBinder();

        assertTrue(binder.delayedBind(AdminMenu.class).canBind());
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
    }

    @After
    public void logout()
    {
        if (JIRA.gotoLoginPage().isLoggedIn())
        {
            JIRA.getPageBinder().navigateToAndBind(LogoutPage.class).confirmLogout();
        }
    }

}
