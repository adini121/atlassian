package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.component.menu.AdminSideMenu;
import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.jira.JiraTestedProduct;

/**
 *
 */
public class JiraAdminHomePage extends JiraAbstractPage<JiraAdminHomePage> implements AdminHomePage<JiraTestedProduct, JiraAdminHomePage>
{
    protected JiraAdminHomePage(JiraTestedProduct testedProduct)
    {
        super(testedProduct, "/secure/admin");
    }

    public AdminSideMenu getAdminSideMenu()
    {
        return new AdminSideMenu(testedProduct);
    }

    
}
