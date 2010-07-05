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
 * Object for interacting with the Admin menu in the JIRA header.
 * TODO: extend for all available links.
 */
public class AdminMenu extends DropdownMenu
{

    public AdminMenu(WebDriver driver)
    {
        super(Search.findElementWithChildElement(By.cssSelector("#main-nav li"), By.id("admin_link"), driver), driver);
    }

    public PluginsPage gotoPluginsPage()
    {
        open().click("plugins_lnk");

        return JiraPage.PLUGINS.get(getDriver(), true);

    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        open().click("license_details_lnk");

        return JiraPage.LICENSEDETAILS.get(getDriver(), true);
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        open().click("user_browser_lnk");

        return JiraPage.USERBROWSER.get(getDriver(), true);
    }

}