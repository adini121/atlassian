package com.atlassian.webdriver.browsers;

import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import com.atlassian.webdriver.it.pageobjects.page.UserAgentPage;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * 
 */
@TestBrowser("htmlunit")
public class TestHtmlUnit_WebDriver extends AbstractFileBasedServerTest
{

    private static final String HTML_UNIT_USER_AGENT = "Gecko/20100401 Firefox/3.6.3";

    UserAgentPage userAgentPage;

    @Before
    public void init()
    {
        userAgentPage = product.getPageBinder().navigateToAndBind(UserAgentPage.class);
    }

    @Test
    @TestBrowser("htmlunit")
    public void testHtmlUnit() throws Exception
    {
        String formattedError = String.format("The user agent: '%s' does not contain '%s'",
            userAgentPage.getUserAgent(), HTML_UNIT_USER_AGENT);
        assertTrue(formattedError, userAgentPage.getUserAgent().contains(HTML_UNIT_USER_AGENT));
    }
}
