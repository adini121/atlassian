package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserAutoInstaller;
import com.atlassian.browsers.BrowserConfig;
import com.atlassian.browsers.BrowserConfiguration;
import com.atlassian.browsers.InstallConfigurator;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @since 2.0
 */
public class AutoInstallConfiguration
{
    /**
     * Setup a browser using system props to determine which, using maven's taget dir as a temp dir.
     */
    public static BrowserConfig setupBrowser()
    {
        return setupBrowser(new WebDriverBrowserConfiguration());
    }

    /**
     * Setup a browser specifying the browser and temp dir.
     */
    public static BrowserConfig setupBrowser(final BrowserConfiguration browserConfiguration)
    {
        final AtomicReference<BrowserConfig> ref = new AtomicReference<BrowserConfig>();
        final BrowserAutoInstaller browserAutoInstaller = new BrowserAutoInstaller(browserConfiguration, new InstallConfigurator()
        {
            @Override
            public void setupBrowser(@Nonnull BrowserConfig browserConfig)
            {
                ref.set(browserConfig);
            }

            @Override
            public void setupFirefoxBrowser(final BrowserConfig browserConfig)
            {
                System.setProperty("webdriver.firefox.bin", browserConfig.getBinaryPath());
            }

            @Override
            public void setupChromeBrowser(final BrowserConfig browserConfig)
            {
                System.setProperty("webdriver.chrome.bin", browserConfig.getBinaryPath());
            }
        });
        browserAutoInstaller.setupBrowser();
        return ref.get();
    }

}