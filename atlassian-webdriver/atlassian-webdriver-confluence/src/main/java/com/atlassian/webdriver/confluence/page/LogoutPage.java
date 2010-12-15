package com.atlassian.webdriver.confluence.page;

import com.atlassian.pageobjects.navigator.ValidateState;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import org.openqa.selenium.WebDriver;

/**
 * Extends the Login Page as Confluence redirects the user back to the login
 * page when they log out.
 */
public class LogoutPage extends ConfluenceLoginPage
{
    private static final String URI = "/logout.action";


    @Override
    public String getUrl()
    {
        return URI;
    }
}
