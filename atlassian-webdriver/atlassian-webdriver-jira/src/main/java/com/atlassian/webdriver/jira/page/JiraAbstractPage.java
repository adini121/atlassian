package com.atlassian.webdriver.jira.page;


import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.WaitUntil;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.DashboardMenu;
import com.atlassian.webdriver.jira.component.menu.JiraUserMenu;
import com.atlassian.webdriver.pageobjects.page.UserDiscoverable;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Proveds a set of common functions that a JIRA page object can do.
 * Such as getting the admin menu.
 * Sets the base url for the WebDrivePage class to use which is defined in the jira-base-url system property.
 */
public abstract class JiraAbstractPage implements Page, UserDiscoverable
{
    @Inject
    protected PageNavigator pageNavigator;

    @Inject
    protected AtlassianWebDriver driver;

    public boolean isLoggedIn()
    {
        return getHeader().isLoggedIn();
    }

    public boolean isLoggedInAsUser(User user)
    {
        return getHeader().isLoggedInAsUser(user);
    }

    public boolean isAdmin()
    {
        return getHeader().isAdmin();
    }

    public JiraHeader getHeader()
    {
        return pageNavigator.build(JiraHeader.class);
    }

    public DashboardMenu getDashboardMenu()
    {
        return getHeader().getDashboardMenu();
    }

    public AdminMenu getAdminMenu()
    {
        if (isAdmin())
        {
            return getHeader().getAdminMenu();
        }
        else
        {
            throw new RuntimeException("Tried to get the admin menu but the current user does not have access to it.");
        }
    }

    public JiraUserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return getHeader().getUserMenu();
        }
        else
        {
            throw new RuntimeException("Tried to get the user menu but the user is not logged in.");
        }
    }

    /**
     * The default doWait for JIRA is to wait for the footer to be located.
     */
    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(By.className("footer"));
    }
}