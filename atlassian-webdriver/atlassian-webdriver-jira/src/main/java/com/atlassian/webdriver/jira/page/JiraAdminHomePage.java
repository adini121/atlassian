package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.jira.JiraTestedProduct;

/**
 *
 */
public class JiraAdminHomePage extends JiraAdminAbstractPage<JiraAdminHomePage> implements AdminHomePage<JiraTestedProduct, JiraAdminHomePage>
{
    protected JiraAdminHomePage(JiraTestedProduct testedProduct)
    {
        super(testedProduct, "/secure/admin");
    }

    
}
