package com.atlassian.webdriver.utils;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @since v2.1
 */
public class WebDriverUtil
{
    private static Browser latestBrowser;

    private WebDriverUtil() {}

    public static void setLatestBrowser(Browser browser)
    {
        latestBrowser = browser;
    }

    public static Browser getLatestBrowser()
    {
        return latestBrowser;
    }

    public static boolean isFirefox(WebDriver driver)
    {
        return getRealDriver(driver) instanceof FirefoxDriver;
    }

    public static boolean isChrome(WebDriver driver)
    {
        return getRealDriver(driver) instanceof ChromeDriver;
    }

    public static boolean isHtmlUnit(WebDriver driver)
    {
        return getRealDriver(driver) instanceof HtmlUnitDriver;
    }

    public static boolean isIE(WebDriver driver)
    {
        return getRealDriver(driver) instanceof InternetExplorerDriver;
    }

    /**
     * Helper to get the real driver implementation
     */
    private static WebDriver getRealDriver(WebDriver driver)
    {
        if (driver instanceof AtlassianWebDriver)
        {
            driver = ((AtlassianWebDriver) driver).getDriver();
        }

        return driver;
    }
}
