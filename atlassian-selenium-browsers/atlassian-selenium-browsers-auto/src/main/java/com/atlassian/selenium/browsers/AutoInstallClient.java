package com.atlassian.selenium.browsers;

import com.atlassian.selenium.SeleniumConfiguration;
import com.atlassian.selenium.SingleBrowserSeleniumClient;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 * @since 2.0
 */
public class AutoInstallClient extends SingleBrowserSeleniumClient
{
    private static final AutoInstallClient CLIENT = new AutoInstallClient(AutoInstallConfiguration.getInstance());

    static
    {
        CLIENT.start();
    }

    private AutoInstallClient(SeleniumConfiguration config)
    {
        super(config);
    }

    public static AutoInstallClient seleniumClient()
    {
        return CLIENT;
    }
}
