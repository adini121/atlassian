package webdriver.browsers;

import com.atlassian.browsers.InstallConfigurator;
import com.atlassian.browsers.BrowserAutoInstaller;
import com.atlassian.browsers.browser.BrowserConfig;


/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AutoInstallConfiguration
{


    public AutoInstallConfiguration()
    {

        BrowserAutoInstaller browserAutoInstaller = new BrowserAutoInstaller(WebDriverBrowserConfiguration.class,
            new InstallConfigurator() {

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
            }
        );

        browserAutoInstaller.setupBrowser();

    }

}