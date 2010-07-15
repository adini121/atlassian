package com.atlassian.webdriver.component.jira.menu;

import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.page.jira.JiraPage;
import com.atlassian.webdriver.page.jira.LogoutPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Object for interacting with the User Menu in the JIRA Header.
 * TODO: extend with full list of available links.
 */
public class UserMenu extends AuiDropdownMenu
{

    public UserMenu(WebDriver driver)
    {
        super(By.id("header-details-user"), driver);
    }

    public LogoutPage logout()
    {
        activate("log_out");

        return JiraPage.LOGOUTPAGE.get(getDriver(), true);
    }

}