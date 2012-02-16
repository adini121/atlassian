package com.atlassian.webdriver.utils;

import com.atlassian.pageobjects.Browser;
import com.atlassian.pageobjects.util.BrowserUtil;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @since 2.1.0
 */
public class WebDriverUtil
{
    private WebDriverUtil() {}

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
