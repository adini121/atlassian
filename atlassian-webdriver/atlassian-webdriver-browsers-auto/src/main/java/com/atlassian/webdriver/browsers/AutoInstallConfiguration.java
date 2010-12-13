package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.InstallConfigurator;
import com.atlassian.browsers.BrowserAutoInstaller;
import com.atlassian.browsers.BrowserConfig;


/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AutoInstallConfiguration
{

    public AutoInstallConfiguration()
    {

        BrowserAutoInstaller browserAutoInstaller = new BrowserAutoInstaller(new WebDriverBrowserConfiguration(),
            new InstallConfigurator() {

                @Override
                public void setupFirefoxBrowser(final BrowserConfig browserConfig)
                {
                    System.setProperty("webdriver.firefox.bin", browserConfig.getBinaryPath());
                    //System.setProperty("webdriver.firefox.profile", browserConfig.getProfilePath());
                }

                @Override
                public void setupChromeBrowser(final BrowserConfig browserConfig)
                {
                    System.setProperty("webdriver.chrome.bin", browserConfig.getBinaryPath());
                }
            }
        );

        browserAutoInstaller.setupBrowser();
    }

}