package com.atlassian.webdriver.component.jira.menu;

import com.atlassian.webdriver.component.menu.DropdownMenu;
import com.atlassian.webdriver.page.jira.JiraPage;
import com.atlassian.webdriver.page.jira.LogoutPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class UserMenu extends DropdownMenu
{

    public UserMenu(WebDriver driver)
    {
        super(By.id("header-details-user"), driver);
    }

    public LogoutPage logout()
    {
        open();

        menuItem.findElement(By.id("log_out")).click();

        return JiraPage.LOGOUT.get(getDriver());
    }

}