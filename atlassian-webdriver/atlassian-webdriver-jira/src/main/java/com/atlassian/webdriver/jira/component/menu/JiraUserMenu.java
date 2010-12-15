package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.Init;
import com.atlassian.webdriver.jira.page.PluginsPage;
import com.atlassian.webdriver.pageobjects.ClickableLink;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.menu.AuiDropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.DropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.UserMenu;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.LogoutPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Object for interacting with the User Menu in the JIRA Header.
 */
public class JiraUserMenu implements DropdownMenu<JiraUserMenu>, UserMenu
{
    @Inject
    PageNavigator pageNavigator;

    @ClickableLink(id = "log_out", nextPage = LogoutPage.class)
    WebDriverLink<LogoutPage> logoutLink;


    private AuiDropdownMenu userMenu;

    @Init
    public void initialise()
    {
        userMenu = pageNavigator.build(AuiDropdownMenu.class, By.id("header-details-user"));
    }

    public LogoutPage logout()
    {
        return logoutLink.activate();
    }

    public JiraUserMenu open()
    {
        userMenu.open();
        return this;
    }

    public boolean isOpen()
    {
        return userMenu.isOpen();
    }

    public JiraUserMenu close()
    {
        userMenu.close();
        return this;
    }
}