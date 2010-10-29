package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import org.openqa.selenium.WebDriver;

/**
 * Extends the Login Page as Confluence redirects the user back to the login
 * page when they log out.
 */
public class LogoutPage extends ConfluenceLoginPage
{
    private static final String URI = "/logout.action";

    public LogoutPage(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }

    @Override
    public void doCheck(final String uri, final boolean activated)
    {
        super.doCheck(ConfluenceLoginPage.URI, activated);
    }
}
