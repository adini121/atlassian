package com.atlassian.webdriver.pageobjects;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.DefaultAtlassianWebDriver;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 *
 */
public class DefaultWebDriverTester implements WebDriverTester
{
    private final AtlassianWebDriver webDriver;
    private static final Logger log = LoggerFactory.getLogger(DefaultWebDriverTester.class);
    private Iterable<Object> injectables;

    public DefaultWebDriverTester()
    {
        webDriver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();
        injectables = Arrays.<Object>asList(webDriver);
    }

    public AtlassianWebDriver getDriver()
    {
        return webDriver;
    }

    public Iterable<Object> getInjectables()
    {
        return injectables;
    }

    public void gotoUrl(String url)
    {
        log.debug("Navigating to URL: " + url);
        webDriver.get(url);
    }
}
