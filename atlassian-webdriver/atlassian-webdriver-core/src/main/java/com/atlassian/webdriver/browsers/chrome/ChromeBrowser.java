package com.atlassian.webdriver.browsers.chrome;

import com.atlassian.browsers.BrowserConfig;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @since 2.1
 */
public class ChromeBrowser
{
    private static final Logger log = LoggerFactory.getLogger(ChromeBrowser.class);

    private ChromeBrowser()
    {
    }

    public static ChromeDriver getChromeDriver()
    {
        return new ChromeDriver();
    }

    /**
     * Configures a ChromeDriver based on the browserConfig
     * @param browserConfig browser config that points to the binary path of the browser and
     * optional profile
     * @return A configured ChromeDriver based on the browserConfig passed in
     */
    public static ChromeDriver getChromeDriver(BrowserConfig browserConfig)
    {
        if (browserConfig != null)
        {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.binary", browserConfig.getBinaryPath());

            if (browserConfig.getProfilePath() != null)
            {
                File profilePath = new File(browserConfig.getProfilePath());
                File chromeDriverFile = new File(profilePath, "chromedriver");

                if (chromeDriverFile.exists())
                {
                    String pathToChromeDriver = System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeDriverFile.getAbsolutePath());
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, pathToChromeDriver);
                }
            }

            return new ChromeDriver(capabilities);
        }

        // Fall back on default chrome driver
        return getChromeDriver();
    }

    /**
     * Gets a chrome driver based on the browser path based in
     * @param browserPath the path to the chrome binary to use for the chrome driver.
     * @return A ChromeDriver that is using the binary at the browserPath
     */
    public static ChromeDriver getChromeDriver(String browserPath)
    {
        if (browserPath != null)
        {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.binary", browserPath);

            return new ChromeDriver(capabilities);
        }

        // Fall back on default chrome driver
        log.info("Browser path was null, falling back to default chrome driver.");
        return getChromeDriver();
    }

}
