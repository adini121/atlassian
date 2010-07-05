package com.atlassian.webdriver.test.jira;

import com.atlassian.webdriver.component.jira.user.User;
import com.atlassian.webdriver.page.jira.DashboardPage;
import com.atlassian.webdriver.page.jira.JiraPage;
import com.atlassian.webdriver.page.jira.LoginPage;
import com.atlassian.webdriver.page.jira.LogoutPage;
import com.atlassian.webdriver.test.WebDriverTest;

import static org.junit.Assert.assertTrue;

/**
 * This is the Base class that all Jira tests should extend.
 * It exposes useful behaviour that a test might take advantage of.
 */
public class JiraWebDriverTest extends WebDriverTest
{

    /**
     * Logs in a given user.
     * If the login fails an assert is thrown.
     * @param user The user to login in.
     * @return The DashboardPage that is displayed after the user is logged in.
     */
    public DashboardPage login(User user)
    {
        LoginPage loginPage = JiraPage.LOGIN.get(driver);
        DashboardPage dashboard = loginPage.login(user);

        assertTrue("User: " + user.getUsername() + " failed to login", dashboard.isLoggedInAsUser(user));

        return dashboard;
    }

    public LogoutPage logout()
    {
        return JiraPage.LOGOUT.get(driver);
    }

}
