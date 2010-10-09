package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import org.openqa.selenium.WebDriver;

/**
 * Page object implementation for the Logout page in JIRA. 
 */
public class LogoutPage extends JiraAbstractPage<LogoutPage>
{

    private static final String URI = "/secure/Logout!default.jspa";

    public LogoutPage(JiraTestedProduct jiraTestedProduct)
    {
        super(jiraTestedProduct, URI);
    }
}