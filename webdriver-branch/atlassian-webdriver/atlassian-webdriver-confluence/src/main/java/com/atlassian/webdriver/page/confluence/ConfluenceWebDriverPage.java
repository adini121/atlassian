package com.atlassian.webdriver.page.confluence;

import com.atlassian.webdriver.component.confluence.menu.BrowseMenu;
import com.atlassian.webdriver.component.confluence.menu.UserMenu;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.WebDriverPage;
import com.atlassian.webdriver.test.confluence.ConfluenceWebDriverTest;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class ConfluenceWebDriverPage extends WebDriverPage
{

    public static final String BASE_URL = System.getProperty("confluence-base-url", "http://localhost:1990/wiki");

    public ConfluenceWebDriverPage(WebDriver driver)
    {
        super(driver, BASE_URL);
    }

    @Override
    public boolean isAdmin()
    {
        return Check.elementExists(By.id("administration-link"));
    }

    @Override
    public boolean isLoggedIn()
    {
        return Check.elementExists(By.id("user-menu-link"));
    }

    @Override
    public boolean isLoggedInAsUser(final User user)
    {
        if (isLoggedIn())
        {
            return driver.findElement(By.id("user-menu-link"))
                    .getText()
                    .equals(user.getFullName());
        }

        return false;
    }

    public BrowseMenu getBrowseMenu()
    {
        if (isLoggedIn())
        {
            return new BrowseMenu(driver);
        }
        else
        {
            throw new RuntimeException("Tried to get the browse menu but the user is not logged in.");
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

    /**
     * Must override the WebDriverPage version as need to handle the Administrator Access
     * page.
     * @param URI
     * @param activated
     */
    @Override
    public void get(String URI, boolean activated)
    {
        super.get(URI, activated);

        //Check whether we are on the admin access page and it wasn't requested.
        //If this is the case log in the user automatically.
        if (!URI.equals(AdministratorAccessPage.URI) && at(AdministratorAccessPage.URI))
        {
            ConfluencePage.ADMINACCESSPAGE.get(driver, true).login(ConfluenceWebDriverTest.getLoggedInUser());
        }


    }

}
