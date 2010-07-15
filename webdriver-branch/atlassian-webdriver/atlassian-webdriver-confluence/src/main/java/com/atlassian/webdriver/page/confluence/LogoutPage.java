package com.atlassian.webdriver.page.confluence;

import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class LogoutPage extends ConfluenceWebDriverPage
{
    private static final String URI = "/logout.action";

    public LogoutPage(WebDriver driver)
    {
        super(driver);
    }

    public LogoutPage get(final boolean activated)
    {
        get(URI, activated);

        return this;
    }
}
