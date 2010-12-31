package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import org.openqa.selenium.WebDriverException;

import java.net.SocketException;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 */
public enum WebDriverBrowserAutoInstall
{
    INSTANCE;

    private AtlassianWebDriver driver;

    WebDriverBrowserAutoInstall() {
        BrowserConfig browserConfig = AutoInstallConfiguration.setupBrowser();
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    driver.quit();
                } catch (WebDriverException e)
                {
                    // WebDriver's RemoteWebDriver will try to close the browser
                    // when it has already been closed, and as a result throws a
                    // WebDriverException caused by a SocketException. We want
                    // to ignore this exception.
                    if (!(e.getCause() instanceof SocketException))
                    {
                        throw e;
                    }
                }
            }
        });

        driver = WebDriverFactory.getDriver(browserConfig);

    }

    public AtlassianWebDriver getDriver()
    {
        return driver;
    }
}