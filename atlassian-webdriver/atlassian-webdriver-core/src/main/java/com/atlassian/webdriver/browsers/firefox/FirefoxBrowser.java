package com.atlassian.webdriver.browsers.firefox;

import com.atlassian.browsers.BrowserConfig;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * A helper utility for obtaining a FirefoxDriver.
 * @since v2.0
 */
public final class FirefoxBrowser
{
    private static final Logger log = LoggerFactory.getLogger(FirefoxBrowser.class);

    private FirefoxBrowser()
    {
        throw new IllegalStateException("FirefoxBrwoser is not constructable");
    }

    /**
     * Gets the default FirefoxDriver that tries to use the default system paths
     * for where the firefox binary should be
     * @return Default configured FirefoxDriver
     */
    public static FirefoxDriver getFirefoxDriver()
    {
        FirefoxBinary firefox = new FirefoxBinary();
        setSystemProperties(firefox);
        return new FirefoxDriver(firefox, null);
    }

    /**
     * Configures a FirefoxDriver based on the browserConfig
     * @param browserConfig browser config that points to the binary path of the browser and
     * optional profile
     * @return A configured FirefoxDriver based on the browserConfig passed in
     */
    public static FirefoxDriver getFirefoxDriver(BrowserConfig browserConfig)
    {
        FirefoxBinary firefox;
        FirefoxProfile profile = null;

        if (browserConfig != null)
        {
            firefox = new FirefoxBinary(new File(browserConfig.getBinaryPath()));
            if (browserConfig.getProfilePath() != null)
            {
                File profilePath = new File(browserConfig.getProfilePath());

                profile = new FirefoxProfile();

                File extensionsDir = new File(profilePath, "extensions");
                if (extensionsDir.exists())
                {
                    // Filter the extentions path to only include directories
                    for (File extension : extensionsDir.listFiles(
                            new FileFilter()
                            {
                                public boolean accept(final File file)
                                {
                                    if (file.isDirectory())
                                    {
                                        return true;
                                    }

                                    return false;
                                }
                            }))
                    {
                        try
                        {
                            profile.addExtension(extension);
                        }
                        catch (IOException e)
                        {
                            log.error("Unable to load extension: " + extension, e);
                        }
                    }
                }
            }

            setSystemProperties(firefox);
            return new FirefoxDriver(firefox, profile);
        }

        // Fall back on default firefox driver
        return getFirefoxDriver();
    }

    /**
     * Gets a firefox driver based on the browser path based in
     * @param browserPath the path to the firefox binary to use for the firefox driver.
     * @return A FirefoxDriver that is using the binary at the browserPath
     */
    public static FirefoxDriver getFirefoxDriver(String browserPath)
    {
        FirefoxBinary firefox;

        if (browserPath != null)
        {
            firefox = new FirefoxBinary(new File(browserPath));
            setSystemProperties(firefox);
            return new FirefoxDriver(firefox, null);
        }

        // Fall back on default firefox driver
        return getFirefoxDriver();
    }

    /**
     * Sets up system properties on the firefox driver.
     * @param firefox
     */
    private static void setSystemProperties(FirefoxBinary firefox)
    {
        if (System.getProperty("DISPLAY") != null)
        {
            firefox.setEnvironmentProperty("DISPLAY", System.getProperty("DISPLAY"));
        }
    }
}
