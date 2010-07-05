package com.atlassian.webdriver.page.jira;


import com.atlassian.webdriver.component.jira.menu.UserMenu;
import com.atlassian.webdriver.component.jira.user.User;
import com.atlassian.webdriver.component.jira.menu.AdminMenu;
import com.atlassian.webdriver.component.menu.DashboardMenu;
import com.atlassian.webdriver.page.WebDriverPage;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class JiraWebDriverPage extends WebDriverPage
{

    public JiraWebDriverPage(WebDriver driver)
    {
        super(driver, System.getProperty("jira-base-url", "http://localhost:2990/jira"));
    }

    public boolean isLoggedIn()
    {
        return !driver.findElement(By.id("header-details-user"))
                .findElement(By.tagName("a"))
                .getText()
                .equals("Log In");
    }

    public boolean isLoggedInAsUser(User user)
    {
        return driver.findElement(By.id("header-details-user"))
                .findElement(By.tagName("a"))
                .getText()
                .equals(user.getFullName());
    }

    public boolean isAdmin()
    {
        return Check.elementExists(By.cssSelector("#header #menu a#admin_link"));
    }

    public String getPageSource()
    {
        return driver.getPageSource();
    }

    public DashboardMenu getDashboardMenu()
    {
        return new DashboardMenu(driver);
    }

    //TODO: Should this throw an exception instead??
    public AdminMenu getAdminMenu()
    {
        return isAdmin() ? new AdminMenu(driver) : null;
    }

    //TODO: Should this throw an exception instead??
    public UserMenu getUserMenu()
    {
        return isLoggedIn() ? new UserMenu(driver) : null;
    }

}