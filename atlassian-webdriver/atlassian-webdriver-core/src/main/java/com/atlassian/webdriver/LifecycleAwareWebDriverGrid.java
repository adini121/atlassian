package com.atlassian.webdriver;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.browsers.AutoInstallConfiguration;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple lifecycle aware webdriver helper that will setup
 * auto browsers and then retrieve a driver from the factory.
 * Once the driver is running it will be re-used if the same
 * browser property is retrieved again.
 * When the runtime is shutdown it will handle cleaning up the browser.
 *
 * @since 2.1.0
 */
public class LifecycleAwareWebDriverGrid
{
    private static Map<String,AtlassianWebDriver> drivers = new HashMap<String,AtlassianWebDriver>();
    private static AtlassianWebDriver currentDriver;

    private LifecycleAwareWebDriverGrid() {}

    /**
     * Retrieves the driver from the {@link WebDriverFactory}. If an instance
     * of the driver is already running then it will be re-used instead of
     * creating a new instance.
     *
     * @return an instance of the driver
     */
    public static AtlassianWebDriver getDriver()
    {
        String browserProperty = WebDriverFactory.getBrowserProperty();
        if (browserIsConfigured(browserProperty))
        {
            AtlassianWebDriver driver = drivers.get(browserProperty);
            currentDriver = driver;
            return driver;
        }

        BrowserConfig browserConfig = AutoInstallConfiguration.setupBrowser();
        AtlassianWebDriver driver = WebDriverFactory.getDriver(browserConfig);
        drivers.put(browserProperty, driver);
        currentDriver = driver;

        addShutdownHook(browserProperty, driver);
        return driver;
    }

    public static AtlassianWebDriver getCurrentDriver()
    {
        Preconditions.checkState(currentDriver != null, "The current driver has not been initialised");
        return currentDriver;
    }

    public static Supplier<AtlassianWebDriver> currentDriverSupplier()
    {
        return new Supplier<AtlassianWebDriver>()
        {
            @Override
            public AtlassianWebDriver get()
            {
                return getCurrentDriver();
            }
        };
    }

    private static boolean browserIsConfigured(String browserProperty)
    {
        return drivers.containsKey(browserProperty);
    }

    private static void addShutdownHook(final String browserProperty, final WebDriver driver) {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    drivers.remove(browserProperty);

                    if (driver.equals(currentDriver))
                    {
                        currentDriver = null;
                    }

                    driver.quit();
                } catch (WebDriverException e)
                {
                    if(!isKnownQuitException(e))
                    {
                        throw e;
                    }
                }
            }
        });
    }

    /**
     * WebDriver's RemoteWebDriver will try to close the browser
     * when it has already been closed (e.g if xvfb is stopped
     * and kills the browser), and as a result throws a
     * WebDriverException.
     *
     * This checks if the exception is a known and expected.
     */
    private static boolean isKnownQuitException(WebDriverException e) {
        Throwable cause = e.getCause();
        String msg = e.getMessage();

        if (cause instanceof SocketException)
            return true;

        // Chrome does not have a cause, so need to check the message.
        if (msg != null && msg.indexOf("Chrome did not respond to 'WaitForAllTabsToStopLoading'") >= 0)
            return true;

        return false;
    }
}
