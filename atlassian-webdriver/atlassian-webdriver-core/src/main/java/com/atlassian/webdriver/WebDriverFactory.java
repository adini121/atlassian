package com.atlassian.webdriver;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.utils.Browser;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks the Sytem property webdriver.browser to see what browser driver to return.
 * Defaults to firefox-3.5.
 */
public class WebDriverFactory
{

    private static final Pattern browserPathPattern = Pattern.compile("^([A-Za-z0-9_.-]+):path=(.*)$");

    private WebDriverFactory() {}

    public static AtlassianWebDriver getDriver()
    {
        return getDriver(null);
    }

    public static AtlassianWebDriver getDriver(BrowserConfig browserConfig)
    {
        WebDriver driver;

        String BROWSER = System.getProperty("webdriver.browser", "firefox-3.5");
        String browserPath = null;

        Matcher matcher = browserPathPattern.matcher(BROWSER);

        if (matcher.matches())
        {
            BROWSER = matcher.group(1);
            browserPath = matcher.group(2);
        }

        Browser browserType = Browser.typeOf(BROWSER);

        switch(browserType)
        {
            case FIREFOX:
                FirefoxBinary firefox;

                if (browserPath == null)
                {
                    firefox = new FirefoxBinary(new File(browserConfig.getBinaryPath()));
                }
                else
                {
                    firefox = new FirefoxBinary(new File(browserPath));
                }

                if (System.getProperty("DISPLAY") != null)
                {
                    firefox.setEnvironmentProperty("DISPLAY", System.getProperty("DISPLAY"));
                }
                FirefoxProfile profile = null;

                //profile = new FirefoxProfile(new File(browserConfig.getProfilePath()));

                driver = new FirefoxDriver(firefox, profile);
                break;

            case CHROME:
                if (browserPath != null)
                {
                    System.setProperty("webdriver.chrome.bin", browserPath);
                }
                driver = new ChromeDriver();
                break;
            case IE:
                driver = new InternetExplorerDriver();
                break;
            case HTMLUNIT:
                driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3);
                ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
                break;

            case SAFARI:
            case OPERA:
            default:
                System.err.println("Unknown browser: " + BROWSER + ", defaulting to firefox.");
                driver = new FirefoxDriver();


        }

        return new DefaultAtlassianWebDriver(driver);
    }
}