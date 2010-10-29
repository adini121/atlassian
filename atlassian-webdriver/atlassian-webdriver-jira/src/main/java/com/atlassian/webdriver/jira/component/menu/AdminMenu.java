package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.PluginsPage;
import com.atlassian.webdriver.jira.page.ProjectsViewPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import com.atlassian.webdriver.utils.by.ByJquery;

/**
 * Object for interacting with the Admin menu in the JIRA header.
 * TODO: extend for all available links.
 */
public class AdminMenu extends AuiDropdownMenu<JiraTestedProduct>
{

    public AdminMenu(JiraTestedProduct jiraTestedProduct)
    {
        super(ByJquery.$("#admin_link").parent("li"), jiraTestedProduct);
    }

    public PluginsPage gotoPluginsPage()
    {
        activate("plugins_lnk");

        return getTestedProduct().gotoPage(PluginsPage.class, true);

    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        activate("license_details_lnk");

        return getTestedProduct().gotoPage(LicenseDetailsPage.class, true);
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        activate("user_browser_lnk");

        return getTestedProduct().gotoPage(UserBrowserPage.class, true);
    }

    public ProjectsViewPage gotoProjectsPage()
    {
        activate("view_projects_lnk");

        return getTestedProduct().gotoPage(ProjectsViewPage.class, true);
    }

}