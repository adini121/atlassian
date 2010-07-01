package com.atlassian.webdriver.component.jira.menu;

import com.atlassian.webdriver.component.menu.DropdownMenu;
import com.atlassian.webdriver.page.jira.JiraPage;
import com.atlassian.webdriver.page.jira.LicenseDetailsPage;
import com.atlassian.webdriver.page.jira.PluginsPage;
import com.atlassian.webdriver.page.jira.UserBrowserPage;
import com.atlassian.webdriver.utils.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AdminMenu extends DropdownMenu
{

    public AdminMenu(WebDriver driver)
    {
        super(Search.findElementWithChildElement(By.cssSelector("#main-nav li"), By.id("admin_link"), driver), driver);
    }

    public PluginsPage gotoPluginsPage()
    {
        open();

        menuItem.findElement(By.id("plugins")).click();

        return JiraPage.PLUGINS.get(getDriver());

    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        open();

        menuItem.findElement(By.id("license_details")).click();

        return JiraPage.LICENSEDETAILS.get(getDriver());
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        open();

        menuItem.findElement(By.id("user_browser_lnk")).click();

        return JiraPage.USERBROWSER.get(getDriver());
    }

}