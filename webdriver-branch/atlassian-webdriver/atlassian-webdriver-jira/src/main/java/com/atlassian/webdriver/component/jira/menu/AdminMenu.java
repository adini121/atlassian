package com.atlassian.webdriver.component.jira.menu;

import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
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
public class AdminMenu extends AuiDropdownMenu
{

    public AdminMenu(WebDriver driver)
    {
        super(Search.findElementWithChildElement(By.cssSelector("#main-nav li"), By.id("admin_link"), driver), driver);
    }

    public PluginsPage gotoPluginsPage()
    {
        activate("plugins_lnk");

        return JiraPage.PLUGINSPAGE.get(getDriver(), true);

    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        activate("license_details_lnk");

        return JiraPage.LICENSEDETAILSPAGE.get(getDriver(), true);
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        activate("user_browser_lnk");

        return JiraPage.USERBROWSERPAGE.get(getDriver(), true);
    }

}