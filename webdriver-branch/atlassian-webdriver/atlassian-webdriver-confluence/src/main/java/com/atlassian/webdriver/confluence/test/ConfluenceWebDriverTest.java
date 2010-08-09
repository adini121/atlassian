package com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.confluence.page.ConfluencePage;
import com.atlassian.webdriver.confluence.page.LogoutPage;
import com.atlassian.webdriver.test.WebDriverTest;

import static org.junit.Assert.assertTrue;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ConfluenceWebDriverTest extends WebDriverTest
{

    private static User loggedInUser;

    /**
     * Logs in a given user.
     * If the login fails an assert is thrown.
     * @param user The user to login in.
     * @return The DashboardPage that is displayed after the user is logged in.
     */
    public DashboardPage login(User user)
    {
        DashboardPage dashboard = ConfluencePage.LOGINPAGE.get(driver).login(user);

        assertTrue("User: " + user.getUsername() + " failed to login", dashboard.isLoggedInAsUser(user));

        loggedInUser = user;

        return dashboard;
    }

    public LogoutPage logout()
    {
        loggedInUser = null;
        return ConfluencePage.LOGOUTPAGE.get(driver);
    }

    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(final User loggedInUser)
    {
        ConfluenceWebDriverTest.loggedInUser = loggedInUser;
    }
}
