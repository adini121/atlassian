package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class LogoutPage extends ConfluenceAbstractPage<LogoutPage>
{
    private static final String URI = "/logout.action";

    public LogoutPage(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }
}
