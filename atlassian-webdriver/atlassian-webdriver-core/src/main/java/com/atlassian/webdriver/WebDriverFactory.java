package com.atlassian.webdriver;

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

    public static WebDriver getDriver()
    {
        String BROWSER = System.getProperty("webdriver.browser", "firefox-3.5");

        if (BROWSER.startsWith("firefox"))
        {
            FirefoxBinary firefox = new FirefoxBinary();
            if (System.getProperty("DISPLAY") != null){
                firefox.setEnvironmentProperty("DISPLAY", System.getProperty("DISPLAY"));
            }
            FirefoxProfile profile = null;
            /*
             *  Doesn't work like you'd think.  Need to investigate            
            final String profilePath = System.getProperty("webdriver.firefox.profile");
            if (profilePath != null)
            {
                profile = new FirefoxProfile(new File(profilePath))
                {
                };
            }
            */
            return new FirefoxDriver(firefox, profile);
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