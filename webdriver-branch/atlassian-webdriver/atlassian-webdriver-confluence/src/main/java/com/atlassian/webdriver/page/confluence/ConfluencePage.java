package com.atlassian.webdriver.page.confluence;

import com.atlassian.webdriver.page.Page;

/**
 * Provides a helper class of all the pages that are available in Confluence.
 * eg. ConfluencePage.LOGINPAGE will return an instance of the LoginPage object.
 * TODO: implement all pages
 */
public class ConfluencePage
{
    public static final Page<LoginPage> LOGINPAGE = new Page<LoginPage>(LoginPage.class);
    public static final Page<LogoutPage> LOGOUTPAGE = new Page<LogoutPage>(LogoutPage.class);
    public static final Page<DashboardPage> DASHBOARDPAGE = new Page<DashboardPage>(DashboardPage.class);
    public static final Page<AdministrationPage> ADMINPAGE = new Page<AdministrationPage>(AdministrationPage.class);
    public static final Page<AdministratorAccessPage> ADMINACCESSPAGE = new Page<AdministratorAccessPage>(AdministratorAccessPage.class);
    public static final Page<PluginsPage> PLUGINSPAGE = new Page<PluginsPage>(PluginsPage.class);
    public static final Page<PeopleDirectoryPage> PEOPLE_DIRECTORY_PAGE = new Page<PeopleDirectoryPage>(PeopleDirectoryPage.class);

    private ConfluencePage() {}
}
