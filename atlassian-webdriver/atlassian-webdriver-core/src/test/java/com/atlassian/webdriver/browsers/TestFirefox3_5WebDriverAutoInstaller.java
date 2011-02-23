package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.Assert.assertTrue;
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

        BrowserConfig browserConfig = AutoInstallConfiguration.setupBrowser();
        AtlassianWebDriver driver = WebDriverFactory.getDriver(browserConfig);

        driver.get(TEST_URL);
        driver.waitUntilElementIsLocated(By.tagName("h1"));
        assertEquals(driver.findElement(By.tagName("h1")).getText(), "Hello");
        assertTrue(driver.findElement(By.tagName("div")).getText().contains("Firefox/3.5"));
        driver.quit();

    }
}