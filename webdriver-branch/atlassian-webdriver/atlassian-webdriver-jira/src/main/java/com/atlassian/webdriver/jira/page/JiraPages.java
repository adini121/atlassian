package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.page.user.AddUserPage;
import com.atlassian.webdriver.jira.page.user.DeleteUserPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import com.atlassian.webdriver.jira.page.user.ViewUserPage;
import com.atlassian.webdriver.page.Page;



/**
 * Provides a helper class of all the pages that are available in JIRA.
 * eg. JiraPages.LOGINPAGE will return an instance of the LoginPage object.
 * TODO: implement all pages
 */
public class JiraPages
{
    public static final Page<LoginPage> LOGINPAGE = new Page<LoginPage>(LoginPage.class);
    public static final Page<LogoutPage> LOGOUTPAGE = new Page<LogoutPage>(LogoutPage.class);
    public static final Page<DashboardPage> DASHBOARDPAGE = new Page<DashboardPage>(DashboardPage.class);
    public static final Page<PluginsPage> PLUGINSPAGE = new Page<PluginsPage>(PluginsPage.class);
    public static final Page<LicenseDetailsPage> LICENSEDETAILSPAGE = new Page<LicenseDetailsPage>(LicenseDetailsPage.class);
    public static final Page<UserBrowserPage> USERBROWSERPAGE = new Page<UserBrowserPage>(UserBrowserPage.class);
    public static final Page<ProjectsViewPage> PROJECTS_VIEW_PAGE = new Page<ProjectsViewPage>(ProjectsViewPage.class);
    public static final Page<ViewAttachmentsSettingsPage> ATTACHMENTS_SETTINGS_VIEW_PAGE = new Page<ViewAttachmentsSettingsPage>(ViewAttachmentsSettingsPage.class);

    public static final Page<AddUserPage> ADD_USER_PAGE = new Page<AddUserPage>(AddUserPage.class);
    public static final Page<ViewUserPage> VIEW_USER_PAGE = new Page<ViewUserPage>(ViewUserPage.class);
    public static final Page<DeleteUserPage> DELETE_USER_PAGE = new Page<DeleteUserPage>(DeleteUserPage.class);

    private JiraPages() {}
}