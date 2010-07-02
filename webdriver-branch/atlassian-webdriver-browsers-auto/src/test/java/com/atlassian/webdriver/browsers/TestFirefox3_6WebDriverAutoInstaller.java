package com.atlassian.webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.VisibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import webdriver.browsers.WebdriverBrowserAutoInstall;

import static org.testng.Assert.assertEquals;

/**
 * 
 */
public class TestFirefox3_6WebDriverAutoInstaller extends WebDriverAutoInstallerTest
{

    @Test
    public void testFirefox_3_6() throws Exception
    {
        System.setProperty("webdriver.browser", "firefox-3.6");

        WebDriver driver = WebdriverBrowserAutoInstall.getDriver();

        driver.get(TEST_URL);
        AtlassianWebDriver.waitUntil(new VisibilityOfElementLocated(By.tagName("h1")));
        assertEquals(driver.findElement(By.tagName("h1")).getText(), "Hello");
        AtlassianWebDriver.quitDriver();

    }

}
