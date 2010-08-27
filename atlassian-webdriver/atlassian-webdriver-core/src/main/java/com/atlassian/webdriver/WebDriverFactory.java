package com.atlassian.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Checks the Sytem property webdriver.browser to see what browser driver to return. Defaults to
 * firefox-3.5.
 */
public class WebDriverFactory
{

    private WebDriverFactory() {}

    public static WebDriver getDriver()
    {
        String BROWSER = System.getProperty("webdriver.browser", "firefox-3.5");

        if (BROWSER.startsWith("firefox"))
        {
            return new FirefoxDriver();
        }
        else if (BROWSER.startsWith("chrome"))
        {
            return new ChromeDriver();
        }
        else if (BROWSER.startsWith("ie"))
        {
            return new InternetExplorerDriver();
        }
        else if (BROWSER.startsWith("htmlunit"))
        {
            return new HtmlUnitDriver();
        }
        else
        {
            System.err.println("Unknown browser: " + BROWSER + ", defaulting to firefox");
            return new FirefoxDriver();
        }
    }
}