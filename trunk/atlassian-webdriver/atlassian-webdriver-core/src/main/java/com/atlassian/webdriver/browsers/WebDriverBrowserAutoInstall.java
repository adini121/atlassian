package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.LifecycleAwareWebDriverGrid;
import com.atlassian.webdriver.WebDriverFactory;
import org.openqa.selenium.WebDriverException;
import org.slf4j.LoggerFactory;

import java.net.SocketException;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 */
public enum WebDriverBrowserAutoInstall
{
    INSTANCE;

    public AtlassianWebDriver getDriver()
    {
        try {
            return LifecycleAwareWebDriverGrid.getDriver();
        } catch (RuntimeException error) {
            LoggerFactory.getLogger(WebDriverBrowserAutoInstall.class).error("Unable to setup browser", error);
            throw error;
        }
    }
}