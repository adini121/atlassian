package it.com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestAdminMenu
{

    private static final JiraTestedProduct JIRA = TestedProductFactory.create(JiraTestedProduct.class, "jira",
            WebDriverBrowserAutoInstall.INSTANCE.getDriver());

    private DashboardPage dashboard;

    @Before
    public void login()
    {
        dashboard = JIRA.gotoLoginPage().loginAsAdmin();
    }

    @After
    public void cleanUpCookies()
    {
        JIRA.getDriver().manage().deleteAllCookies();
    }

    @Test
    public void testCanOpenAdminMenu()
    {
        dashboard.getAdminMenu().open();
    }

}
