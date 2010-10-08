package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.menu.AdminSideMenu;
import com.atlassian.webdriver.product.TestedProduct;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public abstract class JiraAdminAbstractPage<P extends PageObject> extends JiraAbstractPage<P>
{

    // Set of admin links
    private static Set<Object> obs = new HashSet<Object>();

    public JiraAdminAbstractPage(JiraTestedProduct testedProduct, String uri)
    {
        super(testedProduct, uri);
    }

    public AdminSideMenu getAdminSideMenu()
    {
        return new AdminSideMenu(testedProduct);
    }

}
