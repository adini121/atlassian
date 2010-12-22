package com.atlassian.webdriver.pageobjects;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.pageobjects.Tester;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class WebDriverTester implements Tester
{
    private final AtlassianWebDriver webDriver;
    private static final Logger log = LoggerFactory.getLogger(WebDriverTester.class);

    public WebDriverTester()
    {
        webDriver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();
    }

    public AtlassianWebDriver getDriver()
    {
        return webDriver;
    }

    public Map<Class<?>, Object> getInjectables()
    {
        return new HashMap<Class<?>, Object>() {{
            put(WebDriver.class, webDriver);
            put(AtlassianWebDriver.class, webDriver);
        }};
    }

    public void gotoUrl(String url)
    {
        log.debug("Navigating to URL: " + url);
        webDriver.get(url);
    }
}
