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
@TestBrowser("firefox-3.6")
public class TestFirefox3_6WebDriverAutoInstaller extends AbstractFileBasedServerTest
{
    UserAgentPage userAgentPage;

    @Before
    public void init()
    {
        userAgentPage = product.getPageBinder().navigateToAndBind(UserAgentPage.class);
    }

    @Test
    public void testFirefox_3_6() throws Exception
    {
        String formattedError = String.format("The user agent: '%s' does not contain 'Firefox/3.6'",
            userAgentPage.getUserAgent());
        assertTrue(formattedError, userAgentPage.getUserAgent().contains("Firefox/3.6"));
    }

}
