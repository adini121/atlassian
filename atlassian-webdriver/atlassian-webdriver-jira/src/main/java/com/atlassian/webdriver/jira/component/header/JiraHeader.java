package com.atlassian.webdriver.jira.component.header;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.binder.ValidateState;
import com.atlassian.pageobjects.page.Header;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.DashboardMenu;
import com.atlassian.webdriver.jira.component.menu.JiraUserMenu;
import com.atlassian.webdriver.pageobjects.UserDiscoverable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class JiraHeader implements UserDiscoverable, Header
{
    @Inject
    PageBinder pageBinder;

    @Inject
    AtlassianWebDriver driver;
    
    @FindBy(id="header")
    private WebElement headerElement;
    
    private String userName;

    @Init
    public void init()
    {
        By byId = By.id("header-details-user-fullname");

        userName = driver.elementIsVisible(byId) ? driver.findElement(byId).getText() : null;
    }

    public DashboardMenu getDashboardMenu()
    {
        return pageBinder.bind(DashboardMenu.class);
    }
    public AdminMenu getAdminMenu()
    {
        return pageBinder.bind(AdminMenu.class);
    }
    public JiraUserMenu getUserMenu()
    {
        return pageBinder.bind(JiraUserMenu.class);
    }

    public boolean isLoggedIn()
    {
        return userName != null;
    }

    public boolean isLoggedInAsUser(User user)
    {
        return isLoggedIn() && userName.equals(user.getFullName());
    }

    public boolean isAdmin()
    {
        return isLoggedIn() && driver.elementExistsAt(By.id("admin_link"), headerElement);
    }

}
