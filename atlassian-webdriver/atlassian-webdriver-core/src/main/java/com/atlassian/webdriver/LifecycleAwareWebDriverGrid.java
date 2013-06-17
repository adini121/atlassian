package com.atlassian.webdriver;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.browsers.AutoInstallConfiguration;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p/>
 * Simple lifecycle aware Webdriver helper that will setup auto browsers and then retrieve a driver from the factory.
 * Once the driver is running it will be re-used if the same browser property is retrieved again.
 *
 * <p/>
 * When the runtime is shutdown it will handle cleaning up the browser. It also provides a way to manually quit all
 * the registered drivers/browsers via {@link #shutdown()}. When that method is called, the shutdown hooks for those
 * browser will be unregistered.
 *
 * @since 2.1.0
 */
public class LifecycleAwareWebDriverGrid
{
    private static final Logger log = LoggerFactory.getLogger(LifecycleAwareWebDriverGrid.class);
    private final static Map<String,AtlassianWebDriver> drivers = new ConcurrentHashMap<String, AtlassianWebDriver>();
    private volatile static AtlassianWebDriver currentDriver;

    private static final Map<String,WeakReference<Thread>> SHUTDOWN_HOOKS = new ConcurrentHashMap<String, WeakReference<Thread>>();

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

    /**
     * A manual shut down of the registered drivers. This basically resets the grid to a blank state. The shutdown hooks
     * for the drivers that have been closed are also removed.
     *
     */
    public static void shutdown()
    {
        for (Map.Entry<String,AtlassianWebDriver> driver: drivers.entrySet())
        {
            quit(driver.getValue());
            removeHook(driver);
        }
        drivers.clear();
        SHUTDOWN_HOOKS.clear();
        currentDriver = null;
    }

    private static void removeHook(Map.Entry<String, AtlassianWebDriver> driver)
    {
        WeakReference<Thread> hookRef = SHUTDOWN_HOOKS.get(driver.getKey());
        if (hookRef != null && hookRef.get() != null)
        {
            Runtime.getRuntime().removeShutdownHook(hookRef.get());
        }
    }

    private static void quit(AtlassianWebDriver webDriver)
    {
        try
        {
            webDriver.quit();
        }
        catch (WebDriverException e)
        {
            if (!isKnownQuitException(e))
            {
                throw e;
            }
        }
    }

    private static boolean browserIsConfigured(String browserProperty)
    {
        return drivers.containsKey(browserProperty);
    }

    private static void addShutdownHook(final String browserProperty, final WebDriver driver) {
        final Thread quitter = new Thread()
        {
            @Override
            public void run()
            {
                log.debug("Running shut down hook for {}", driver);
                try
                {
                    drivers.remove(browserProperty);
                    if (driver.equals(currentDriver))
                    {
                        currentDriver = null;
                    }
                    // quitting InternetExplorerDriver in a shutdown hook crashes the JVM with a segfault
                    if (!isIeDriver(driver))
                    {
                        log.debug("Quitting {}: {}", driver, getRealDriver(driver));
                        driver.quit();
                    }
                }
                catch (WebDriverException e)
                {
                    if(!isKnownQuitException(e))
                    {
                        throw e;
                    }
                }
            }

            private WebDriver getRealDriver(WebDriver driver)
            {
                return driver instanceof AtlassianWebDriver ? AtlassianWebDriver.class.cast(driver).getDriver() : driver;
            }
        };
        SHUTDOWN_HOOKS.put(browserProperty, new WeakReference<Thread>(quitter));
        Runtime.getRuntime().addShutdownHook(quitter);
    }

    private static boolean isIeDriver(WebDriver driver)
    {
        if (driver instanceof AtlassianWebDriver)
        {
            AtlassianWebDriver wrapperDriver = (AtlassianWebDriver) driver;
            final WebDriver webDriver = wrapperDriver.getDriver();
            return webDriver instanceof InternetExplorerDriver;
        }
        else
        {
            return driver instanceof InternetExplorerDriver;
        }
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
        {
            return true;
        }
        // Chrome does not have a cause, so need to check the message.
        if (msg != null && msg.contains("Chrome did not respond to 'WaitForAllTabsToStopLoading'"))
        {
            return true;
        }
        return false;
    }
}
