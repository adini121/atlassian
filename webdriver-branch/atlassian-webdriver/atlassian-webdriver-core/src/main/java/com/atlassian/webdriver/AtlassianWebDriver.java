package com.atlassian.webdriver;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AtlassianWebDriver
{
    private static WebDriver driver;
    private static Wait<WebDriver> wait;

    public static int WAIT_TIME = 60;

    public static WebDriver getDriver()
    {
        if (driver == null)
        {
            driver = WebDriverFactory.getDriver();
        }

        return driver;
    }

    public static void quitDriver() {

        if (driver != null)
        {
            driver.quit();
            driver = null;
        }

    }

    private static Wait<WebDriver> getWait()
    {
        if (wait == null)
        {
            wait = new WebDriverWait(getDriver(), WAIT_TIME);
        }
        return wait;
    }

    public static void waitUntil(Function func)
    {
        getWait().until(func);
    }
}