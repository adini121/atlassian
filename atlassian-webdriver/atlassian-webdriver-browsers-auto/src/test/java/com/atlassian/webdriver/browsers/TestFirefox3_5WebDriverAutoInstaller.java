package com.atlassian.webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webdriver.browsers.WebDriverBrowserAutoInstall;

import static org.junit.Assert.assertEquals;

/**
 * 
 */
public class TestFirefox3_5WebDriverAutoInstaller extends WebDriverAutoInstallerTest
{
    @Test
    public void testFirefox_3_5() throws Exception
    {
        System.setProperty("webdriver.browser", "firefox-3.5");

        AtlassianWebDriver driver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();

        driver.get(TEST_URL);
        driver.waitUntilElementIsLocated(By.tagName("h1"));
        assertEquals(driver.findElement(By.tagName("h1")).getText(), "Hello");
        //driver.quit();

    }

}