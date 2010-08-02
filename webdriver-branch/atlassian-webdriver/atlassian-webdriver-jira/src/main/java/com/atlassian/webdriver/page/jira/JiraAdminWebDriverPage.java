package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.component.jira.menu.AdminSideMenu;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public abstract class JiraAdminWebDriverPage extends JiraWebDriverPage
{

    // Set of admin links
    private static Set<Object> obs = new HashSet<Object>();

    public JiraAdminWebDriverPage(WebDriver driver)
    {
        super(driver);
    }

    public AdminSideMenu getAdminSideMenu()
    {
        return new AdminSideMenu(driver);
    }

}
