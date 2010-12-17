package com.atlassian.webdriver.jira.component.header;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.ValidateState;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.DashboardMenu;
import com.atlassian.webdriver.jira.component.menu.JiraUserMenu;
import com.atlassian.webdriver.pageobjects.page.UserDiscoverable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class JiraHeader implements UserDiscoverable
{
    @Inject
    PageBinder pageBinder;

    @Inject
    AtlassianWebDriver driver;
    
    @FindBy(id="header")
    private WebElement headerElement;
    
    @FindBy(id = "header-details-user-fullname")
    private WebElement userFillName;

    @ValidateState
    public void validate()
    {
        userFillName.getText();
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
        return !userFillName.getText().equals("Log In");
    }

    public boolean isLoggedInAsUser(User user)
    {
        return userFillName.getText().equals(user.getFullName());
    }

    public boolean isAdmin()
    {
        return driver.elementExistsAt(By.id("admin_link"), headerElement);
    }

}
