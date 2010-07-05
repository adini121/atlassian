package com.atlassian.webdriver;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Exposes a set of common functions to use.
 */
public class AtlassianWebDriver
{
    private static WebDriver driver;
    private static Wait<WebDriver> wait;

    public static int WAIT_TIME = 60;

    /**
     * Will return an instance of the current driver.
     * At the moment there is only one instance of a driver at a time.
     * Once the getDriver command has returned a driver it will continue to return the same one until quitDriver is called.
     * @return a WebDriver instance.
     * @see AtlassianWebDriver#quitDriver()
     */
    public static WebDriver getDriver()
    {
        if (driver == null)
        {
            driver = WebDriverFactory.getDriver();
        }

        return driver;
    }

    /**
     * Quits the current WebDriver instance if there is one.
     */
    public static void quitDriver() {

        if (driver != null)
        {
            driver.quit();
            driver = null;
        }

    }

    /**
     * Returns an instance of a Wait object.
     * @return
     */
    private static Wait<WebDriver> getWait()
    {
        if (wait == null)
        {
            wait = new WebDriverWait(getDriver(), WAIT_TIME);
        }
        return wait;
    }

    /**
     * Utility for making WebDriver wait until the given function specifies to stop waiting.
     * @param func the function to run to check whether to return from the Wait or not.
     */
    public static void waitUntil(Function func)
    {
        getWait().until(func);
    }
}