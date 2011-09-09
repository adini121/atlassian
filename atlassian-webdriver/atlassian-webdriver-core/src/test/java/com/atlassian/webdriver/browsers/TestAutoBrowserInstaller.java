package com.atlassian.webdriver.browsers;

import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.UserAgentPage;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestAutoBrowserInstaller extends AbstractFileBasedServerTest
{
    private static final String HTML_UNIT_USER_AGENT =
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8";

    UserAgentPage userAgentPage;

    @Before
    public void init()
    {
        userAgentPage = product.getPageBinder().navigateToAndBind(UserAgentPage.class);
    }

    @Test
    @TestBrowser("firefox-3.5")
    public void testFirefox_3_5() throws Exception
    {
        String formattedError = String.format("The user agent: '%s' does not contain 'Firefox/3.5'",
            userAgentPage.getUserAgent());
        assertTrue(formattedError, userAgentPage.getUserAgent().contains("Firefox/3.5"));
    }

    @Test
    @TestBrowser("firefox-3.6")
    public void testFirefox_3_6() throws Exception
    {
        String formattedError = String.format("The user agent: '%s' does not contain 'Firefox/3.6'",
            userAgentPage.getUserAgent());
        assertTrue(formattedError, userAgentPage.getUserAgent().contains("Firefox/3.6"));
    }

    @Test
    @TestBrowser("htmlunit")
    public void testHtmlUnit() throws Exception
    {
        String formattedError = String.format("The user agent: '%s' does not contain '%s'",
            userAgentPage.getUserAgent(), HTML_UNIT_USER_AGENT);
        assertTrue(formattedError, userAgentPage.getUserAgent().equals(HTML_UNIT_USER_AGENT));
    }
}
