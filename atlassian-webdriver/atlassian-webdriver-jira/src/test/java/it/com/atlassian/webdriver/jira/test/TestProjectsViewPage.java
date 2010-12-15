package it.com.atlassian.webdriver.jira.test;

import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.ProjectsViewPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestProjectsViewPage
{
    private static final JiraTestedProduct JIRA = TestedProductFactory.create(JiraTestedProduct.class);

    private ProjectsViewPage projectsViewPage;

    @Test
    public void testProjectsViewPage()
    {
        projectsViewPage = JIRA.gotoLoginPage().loginAsSysAdmin(ProjectsViewPage.class);
    }

    @After
    public void cleanUpCookies()
    {
        JIRA.getTester().getDriver().manage().deleteAllCookies();
    }

}
