package com.atlassian.webdriver.page.jira;

import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
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