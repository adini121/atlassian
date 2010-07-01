package com.atlassian.webdriver.page.jira;


import com.atlassian.webdriver.component.jira.menu.UserMenu;
import com.atlassian.webdriver.component.jira.user.User;
import com.atlassian.webdriver.component.jira.menu.AdminMenu;
import com.atlassian.webdriver.component.menu.DashboardMenu;
import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.VisibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class JiraWebDriverPage implements Page
{

    protected final WebDriver driver;
    protected final Wait<WebDriver> wait;

    //TODO: make this not hard coded
    private final String url = "http://localhost:2990/jira";

    public JiraWebDriverPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
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

    public void waitUntilLocated(By by)
    {
        waitUntilLocated(by, null);
    }

    public void waitUntilLocated(By by, WebElement el)
    {
        wait.until(new VisibilityOfElementLocated(by, el));
    }

    public boolean at(String uri)
    {
        return driver.getCurrentUrl().equals(url + uri);
    }

    public void goTo(String uri)
    {
        driver.get(url + uri);
    }

    public boolean isAdmin()
    {
        return Check.elementExists(By.cssSelector("#header #menu a#admin_link"));
    }

    public WebDriver driver()
    {
        return driver;
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