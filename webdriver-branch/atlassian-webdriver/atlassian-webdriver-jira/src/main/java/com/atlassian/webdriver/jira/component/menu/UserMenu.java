package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.jira.page.LogoutPage;
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

        return JiraPages.LOGOUTPAGE.get(getDriver(), true);
    }

}