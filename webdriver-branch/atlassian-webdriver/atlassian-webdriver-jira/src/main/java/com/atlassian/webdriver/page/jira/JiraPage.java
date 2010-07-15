package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.page.Page;



/**
 * Provides a helper class of all the pages that are available in JIRA.
 * eg. JiraPage.LOGINPAGE will return an instance of the LoginPage object.
 * TODO: implement all pages
 */
public class JiraPage
{
    public static final Page<LoginPage> LOGINPAGE = new Page<LoginPage>(LoginPage.class);
    public static final Page<LogoutPage> LOGOUTPAGE = new Page<LogoutPage>(LogoutPage.class);
    public static final Page<DashboardPage> DASHBOARDPAGE = new Page<DashboardPage>(DashboardPage.class);
    public static final Page<PluginsPage> PLUGINSPAGE = new Page<PluginsPage>(PluginsPage.class);
    public static final Page<LicenseDetailsPage> LICENSEDETAILSPAGE = new Page<LicenseDetailsPage>(LicenseDetailsPage.class);
    public static final Page<UserBrowserPage> USERBROWSERPAGE = new Page<UserBrowserPage>(UserBrowserPage.class);

    private JiraPage() {}
}