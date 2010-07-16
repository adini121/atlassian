package com.atlassian.webdriver.page.jira;


import com.atlassian.webdriver.component.jira.menu.AdminMenu;
import com.atlassian.webdriver.component.jira.menu.UserMenu;
import com.atlassian.webdriver.component.menu.DashboardMenu;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.WebDriverPage;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Proveds a set of common functions that a JIRA page object can do.
 * Such as getting the admin menu.
 * Sets the base url for the WebDrivePage class to use which is defined in the jira-base-url system property.
 */
public abstract class JiraWebDriverPage extends WebDriverPage
{

    public static final String BASE_URL = System.getProperty("jira-base-url", "http://localhost:2990/jira");

    public JiraWebDriverPage(WebDriver driver)
    {
        super(driver, BASE_URL);
    }

    /**
     * Returns a boolean indicating whether the user is logged in or not.
     * @return Whether the user is logged in or not.
     */
    public boolean isLoggedIn()
    {
        return !driver.findElement(By.id("header-details-user"))
                .findElement(By.tagName("a"))
                .getText()
                .equals("Log In");
    }

    /**
     * Checks whether a specific user is logged in and returns a boolean indicating the result.
     * @param user the user to check whether they are logged in or not.
     * @return whether the specified user is logged in.
     */
    public boolean isLoggedInAsUser(User user)
    {
        return driver.findElement(By.id("header-details-user"))
                .findElement(By.tagName("a"))
                .getText()
                .equals(user.getFullName());
    }

    /**
     * Checks if the admin menu is available and if it is
     * assumes the user is an admin user.
     * @return
     */
    public boolean isAdmin()
    {
        return Check.elementExists(By.cssSelector("#header #menu a#admin_link"));
    }

    public DashboardMenu getDashboardMenu()
    {
        return new DashboardMenu(driver);
    }

    public AdminMenu getAdminMenu()
    {
        if (isAdmin())
        {
            return new AdminMenu(driver);
        }
        else
        {
             throw new RuntimeException("Tried to get the admin menu but the current user does not have access to it.");
        }
    }

    public UserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return new UserMenu(driver);
        }
        else
        {
            throw new RuntimeException("Tried to get the user menu but the user is not logged in.");
        }
    }

}