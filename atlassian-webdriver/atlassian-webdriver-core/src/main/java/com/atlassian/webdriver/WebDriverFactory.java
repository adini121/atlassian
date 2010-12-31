package com.atlassian.webdriver;

import com.atlassian.browsers.BrowserConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;

/**
 * Checks the Sytem property webdriver.browser to see what browser driver to return. Defaults to
 * firefox-3.5.
 */
public class WebDriverFactory
{

    private WebDriverFactory() {}

    public static AtlassianWebDriver getDriver()
    {
        return getDriver(null);
    }

    public static AtlassianWebDriver getDriver(BrowserConfig browserConfig)
    {
        WebDriver driver;

        String BROWSER = System.getProperty("webdriver.browser", "firefox-3.5");

        if (BROWSER.startsWith("firefox"))
        {
            FirefoxBinary firefox = new FirefoxBinary(new File(browserConfig.getBinaryPath()));
            if (System.getProperty("DISPLAY") != null){
                firefox.setEnvironmentProperty("DISPLAY", System.getProperty("DISPLAY"));
            }
            FirefoxProfile profile = null;

            //profile = new FirefoxProfile(new File(browserConfig.getProfilePath()));

            driver = new FirefoxDriver(firefox, profile);
        }
        else if (BROWSER.startsWith("chrome"))
        {
            driver = new ChromeDriver();
        }
        else if (BROWSER.startsWith("ie"))
        {
            driver = new InternetExplorerDriver();
        }
        else if (BROWSER.startsWith("htmlunit"))
        {
            driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3);
            ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
        }
        else
        {
            System.err.println("Unknown browser: " + BROWSER + ", defaulting to firefox.");
            driver = new FirefoxDriver();
        }

        return new AtlassianWebDriver(driver);
    }
}