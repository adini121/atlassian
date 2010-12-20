package com.atlassian.webdriver.browsers.pageobjects;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.AutoInstallConfiguration;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.apache.xmlbeans.impl.piccolo.xml.Piccolo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AutoInstallWebDriverTester implements WebDriverTester<AtlassianWebDriver>
{
    private final AtlassianWebDriver webDriver;
    private static final Logger log = LoggerFactory.getLogger(AutoInstallWebDriverTester.class);

    public AutoInstallWebDriverTester()
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
