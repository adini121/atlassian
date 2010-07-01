package com.atlassian.webdriver.page.jira;

import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class LogoutPage extends JiraWebDriverPage
{
    public LogoutPage(WebDriver driver)
    {
        super(driver);
    }

    public LogoutPage get()
    {

        if (!at("/secure/Logout!default.jspa"))
        {
            goTo("/secure/Logout!default.jspa");
        }

        return this;

    }

}