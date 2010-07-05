package com.atlassian.webdriver.test.jira;

import com.atlassian.webdriver.component.jira.user.User;
import com.atlassian.webdriver.page.jira.DashboardPage;
import com.atlassian.webdriver.page.jira.JiraPage;
import com.atlassian.webdriver.page.jira.LoginPage;
import com.atlassian.webdriver.test.WebDriverTest;

import static org.junit.Assert.assertTrue;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class JiraWebDriverTest extends WebDriverTest
{

    public DashboardPage login(User user)
    {
        LoginPage loginPage = JiraPage.LOGIN.get(driver);
        DashboardPage dashboard = loginPage.login(user);

        assertTrue("User: " + user.getUsername() + " failed to login", dashboard.isLoggedInAsUser(user));

        return dashboard;
    }

}
