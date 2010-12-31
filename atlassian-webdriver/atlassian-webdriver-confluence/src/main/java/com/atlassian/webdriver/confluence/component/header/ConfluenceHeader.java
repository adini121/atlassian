package com.atlassian.webdriver.confluence.component.header;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.component.Header;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.ConfluenceUserMenu;
import com.atlassian.webdriver.confluence.component.UserDiscoverable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class ConfluenceHeader implements Header, UserDiscoverable
{

    @Inject
    AtlassianWebDriver driver;

    @Inject
    PageBinder pageBinder;
    
    @FindBy(id = "header")
    WebElement headerElement;
    
    private final static By USER_MENU_LOCATOR = By.id("user-menu-link");
    private final static By ADMIN_MENU_LOCATOR = By.id("administration-link");


    public boolean isLoggedIn()
    {
        return driver.elementExistsAt(USER_MENU_LOCATOR, headerElement);
    }

    public boolean isAdmin()
    {
        return driver.elementExistsAt(ADMIN_MENU_LOCATOR, headerElement);
    }

    public ConfluenceUserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return pageBinder.bind(ConfluenceUserMenu.class);
        }
        else
        {
            throw new RuntimeException("Tried to get the user menu but the user is not logged in.");
        }
    }

    public BrowseMenu getBrowseMenu()
    {
        return pageBinder.bind(BrowseMenu.class);
    }
}
