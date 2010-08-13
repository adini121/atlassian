package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.PluginsPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.WebDriver;

/**
 * Object for interacting with the Admin menu in the JIRA header.
 * TODO: extend for all available links.
 */
public class AdminMenu extends AuiDropdownMenu
{

    public AdminMenu(WebDriver driver)
    {
        super(ByJquery.$("('#admin_link').parent('li')"), driver);
    }

    public PluginsPage gotoPluginsPage()
    {
        activate("plugins_lnk");

        return JiraPages.PLUGINSPAGE.get(getDriver(), true);

    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        activate("license_details_lnk");

        return JiraPages.LICENSEDETAILSPAGE.get(getDriver(), true);
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        activate("user_browser_lnk");

        return JiraPages.USERBROWSERPAGE.get(getDriver(), true);
    }

}