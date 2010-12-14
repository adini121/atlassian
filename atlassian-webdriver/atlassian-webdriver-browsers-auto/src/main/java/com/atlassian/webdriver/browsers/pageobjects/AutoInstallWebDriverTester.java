package com.atlassian.webdriver.browsers.pageobjects;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AutoInstallWebDriverTester implements WebDriverTester<AtlassianWebDriver>
{
    private final AtlassianWebDriver webDriver;
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
        webDriver.get(url);
    }
}
