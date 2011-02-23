package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserAutoInstaller;
import com.atlassian.browsers.BrowserConfig;
import com.atlassian.browsers.InstallConfigurator;

import java.util.concurrent.atomic.AtomicReference;


/**
 * 
 *
 * @since 2.0
 */
public class AutoInstallConfiguration
{
    public static BrowserConfig setupBrowser()
    {
        final AtomicReference<BrowserConfig> ref = new AtomicReference<BrowserConfig>();
        BrowserAutoInstaller browserAutoInstaller = new BrowserAutoInstaller(new WebDriverBrowserConfiguration(),
            new InstallConfigurator() {

                @Override
                public void setupFirefoxBrowser(final BrowserConfig browserConfig)
                {
                    ref.set(browserConfig);
                    System.setProperty("webdriver.firefox.bin", browserConfig.getBinaryPath());
                    //System.setProperty("webdriver.firefox.profile", browserConfig.getProfilePath());
                }

                @Override
                public void setupChromeBrowser(final BrowserConfig browserConfig)
                {
                    ref.set(browserConfig);
                    System.setProperty("webdriver.chrome.bin", browserConfig.getBinaryPath());
                }
            }
        );

        browserAutoInstaller.setupBrowser();
        return ref.get();
    }

}