package com.atlassian.webdriver.jira.page;


import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.DelayedBinder;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.DashboardMenu;
import com.atlassian.webdriver.jira.component.menu.JiraUserMenu;
import com.atlassian.webdriver.pageobjects.UserDiscoverable;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Proveds a set of common functions that a JIRA page object can do.
 * Such as getting the admin menu.
 * Sets the base url for the WebDrivePage class to use which is defined in the jira-base-url system property.
 */
public abstract class JiraAbstractPage implements Page
{
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected AtlassianWebDriver driver;

    public JiraHeader getHeader()
    {
        return pageBinder.bind(JiraHeader.class);
    }

    public DashboardMenu getDashboardMenu()
    {
        return getHeader().getDashboardMenu();
    }

    /**
     * The default doWait for JIRA is to wait for the footer to be located.
     */
    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(By.className("footer"));
    }

    public boolean isLoggedIn()
    {
        DelayedBinder<JiraHeader> header = pageBinder.delayedBind(JiraHeader.class);
        return header.canBind() ? header.bind().isLoggedIn() : false;
    }

    public boolean isLoggedInAsUser(User user)
    {
        DelayedBinder<JiraHeader> header = pageBinder.delayedBind(JiraHeader.class);
        return header.canBind() ? header.bind().isLoggedInAsUser(user) : false;
    }

    public boolean isAdmin()
    {
        DelayedBinder<JiraHeader> header = pageBinder.delayedBind(JiraHeader.class);
        return header.canBind() ? header.bind().isAdmin() : false;
    }
}