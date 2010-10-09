package com.atlassian.webdriver.jira;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.jira.page.DashboardPage;
import com.atlassian.webdriver.product.Defaults;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.jira.page.JiraAbstractPage;
import com.atlassian.webdriver.jira.page.JiraAdminHomePage;
import com.atlassian.webdriver.jira.page.JiraLoginPage;
import com.atlassian.webdriver.product.AbstractTestedProduct;
import com.atlassian.webdriver.product.ProductInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 */
@Defaults(contextPath = "/jira", httpPort = 2990)
public class JiraTestedProduct extends AbstractTestedProduct<DashboardPage, JiraAdminHomePage, JiraLoginPage>
{
    public JiraTestedProduct(WebDriver webDriver, ProductInstance productInstance)
    {
        super(webDriver, productInstance);
    }

    public DashboardPage gotoHomePage()
    {
        return gotoPage(DashboardPage.class);
    }

    public JiraAdminHomePage gotoAdminHomePage()
    {
        return gotoPage(JiraAdminHomePage.class);
    }

    public JiraLoginPage gotoLoginPage()
    {
        return gotoPage(JiraLoginPage.class);
    }
}
