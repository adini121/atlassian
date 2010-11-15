package it.com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.jira.page.ViewAttachmentsSettingsPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webdriver.browsers.WebDriverBrowserAutoInstall;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestViewAttachmentsSettingsPage
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
    public void testViewAttachmentSettingsPage()
    {
        dashboard.gotoPage(ViewAttachmentsSettingsPage.class);
    }

}
