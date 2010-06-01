package com.atlassian.selenium.browsers;

import com.atlassian.selenium.SeleniumAssertions;
import com.atlassian.selenium.SeleniumClient;
import com.atlassian.selenium.SeleniumConfiguration;
import com.atlassian.selenium.SeleniumStarter;
import com.atlassian.selenium.SingleBrowserSeleniumClient;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 * @since 2.0
 */
public class AutoInstallClient
{
    private static final SeleniumClient CLIENT;

    private static SeleniumAssertions assertThat;

    static
    {
        AutoInstallConfiguration config = AutoInstallConfiguration.getInstance();
        if (SeleniumStarter.getInstance().isManual())
        {
            SeleniumStarter.getInstance().start(config);
        }

        CLIENT = SeleniumStarter.getInstance().getSeleniumClient(config);
        assertThat = new SeleniumAssertions(CLIENT, config);
    }

    public static SeleniumClient seleniumClient()
    {
        return CLIENT;
    }

    public static SeleniumAssertions assertThat()
    {
        return assertThat;
    }
}
