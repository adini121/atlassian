package com.atlassian.webdriver;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.browsers.firefox.FirefoxBrowser;
import com.atlassian.webdriver.utils.Browser;
import com.atlassian.webdriver.utils.WebDriverUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks the Sytem property webdriver.browser to see what browser driver to return. Defaults to
 * firefox-3.5.
 */
public class WebDriverFactory
{
    private static final Logger log = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final Pattern browserPathPattern = Pattern.compile("^([A-Za-z0-9_.-]+):path=(.*)$");

    private WebDriverFactory() {}

    public static AtlassianWebDriver getDriver()
    {
        return getDriver(null);
    }

    public static String getBrowserProperty()
    {
        return System.getProperty("webdriver.browser", "firefox-3.5");
    }

    public static AtlassianWebDriver getDriver(BrowserConfig browserConfig)
    {
        WebDriver driver;
        String browserPath = null;

        String BROWSER = getBrowserProperty();
        Matcher matcher = browserPathPattern.matcher(BROWSER);

        if (matcher.matches())
        {
            BROWSER = matcher.group(1);
            browserPath = matcher.group(2);
        }

        Browser browserType = Browser.typeOf(BROWSER);

        switch (browserType)
        {
            case FIREFOX:

                if (browserPath == null && browserConfig != null)
                {
                    driver = FirefoxBrowser.getFirefoxDriver(browserConfig);
                }
                else if (browserPath != null)
                {
                    driver = FirefoxBrowser.getFirefoxDriver(browserPath);
                }
                else
                {
                    driver = FirefoxBrowser.getFirefoxDriver();
                }
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
                driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
                ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
                break;

            case SAFARI:
                throw new UnsupportedOperationException("Safari is not a supported Browser Type");
            case OPERA:
                throw new UnsupportedOperationException("Opera is not a supported Browser Type");
            default:
                System.err.println("Unknown browser: " + BROWSER + ", defaulting to firefox.");
                browserType = Browser.FIREFOX;
                driver = new FirefoxDriver();
        }

        WebDriverUtil.setLatestBrowser(browserType);

        return new DefaultAtlassianWebDriver(driver);
    }
}
