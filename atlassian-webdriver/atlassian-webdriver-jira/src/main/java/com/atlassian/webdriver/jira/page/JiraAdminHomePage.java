package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.component.menu.AdminSideMenu;

/**
 *
 */
public class JiraAdminHomePage extends JiraAbstractPage
{
    private final static String URI = "/secure/admin";

    public String getUrl()
    {
        return URI;
    }

    public AdminSideMenu getAdminSideMenu()
    {
        return pageNavigator.build(AdminSideMenu.class);
    }

    
}
