package com.atlassian.webdriver.jira.page;

import org.openqa.selenium.WebDriver;

/**
 * Page object implementation for the Logout page in JIRA. 
 */
public class LogoutPage extends JiraWebDriverPage
{

    private static final String URI = "/secure/Logout!default.jspa";

    public LogoutPage(WebDriver driver)
    {
        super(driver);
    }

    public LogoutPage get(boolean activated)
    {

        get(URI, activated);

        return this;

    }

}