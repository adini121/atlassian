package com.atlassian.webdriver.browsers.chrome;

import com.atlassian.browsers.BrowserConfig;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            ChromeDriverService.Builder chromeServiceBuilder = new ChromeDriverService.Builder();

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.binary", browserConfig.getBinaryPath());
            addCapabilities(capabilities);

            if (browserConfig.getProfilePath() != null)
            {
                File profilePath = new File(browserConfig.getProfilePath());
                File chromeDriverFile = new File(profilePath, "chromedriver");

                if (chromeDriverFile.exists())
                {
                    chromeServiceBuilder.usingChromeDriverExecutable(chromeDriverFile);
                }
            }

            chromeServiceBuilder.usingAnyFreePort();
            setSystemProperties(chromeServiceBuilder);
            ChromeDriverService chromeDriverService = chromeServiceBuilder.build();

            return new ChromeDriver(chromeDriverService, capabilities);
        }

        // Fall back on default chrome driver
        return getChromeDriver();
    }

    /**
     * Add capabilities as defined in the system properties:
     *
     * <dl>
     *    <dt>webdriver.chrome.switches</dt>
     *    <dd>command line switches, separated by commas</dd>
     * </dl>
     *
     */
    private static void addCapabilities(final DesiredCapabilities capabilities)
    {
        String[] switches = StringUtils.split(System.getProperty("webdriver.chrome.switches"), ",");
        if(switches != null && switches.length > 0) {
            List<String> switchList = Arrays.asList(switches);
            log.info("Setting chrome.switches with: " + switchList);
            capabilities.setCapability("chrome.switches", switchList);
        }
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

    /**
     * Sets up system properties on the chrome driver service.
     * @param chromeDriverServiceBuilder the chrome driver service to set environment map on.
     */
    private static void setSystemProperties(ChromeDriverService.Builder chromeDriverServiceBuilder)
    {
        Map<String, String> env = new HashMap<String, String>();

        if (System.getProperty("DISPLAY") != null)
        {
            env.put("DISPLAY", System.getProperty("DISPLAY"));
        }

        chromeDriverServiceBuilder.withEnvironment(env);
    }

}
