package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.PluginsPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.WebDriver;

/**
 * Object for interacting with the Admin menu in the JIRA header.
 * TODO: extend for all available links.
 */
public class AdminMenu extends AuiDropdownMenu<JiraTestedProduct>
{

    public AdminMenu(JiraTestedProduct jiraTestedProduct)
    {
        super(ByJquery.$("('#admin_link').parent('li')"), jiraTestedProduct);
    }

    public PluginsPage gotoPluginsPage()
    {
        activate("plugins_lnk");

        return new PluginsPage(getTestedProduct()).get(true);

    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        activate("license_details_lnk");

        return new LicenseDetailsPage(getTestedProduct()).get(true);
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        activate("user_browser_lnk");

        return new UserBrowserPage(getTestedProduct()).get(true);
    }

}