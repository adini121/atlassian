package com.atlassian.webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.VisibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import webdriver.browsers.WebdriverBrowserAutoInstall;

import static org.testng.Assert.assertEquals;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class TestChrome_5WebDriverAutoInstaller extends WebDriverAutoInstallerTest
{
    @Test
    public void testChrome_5() throws Exception
    {
        System.setProperty("webdriver.browser", "chrome-5");

        WebDriver driver = WebdriverBrowserAutoInstall.getDriver();

        driver.get(TEST_URL);

        AtlassianWebDriver.waitUntil(new VisibilityOfElementLocated(By.tagName("h1")));

        assertEquals(driver.findElement(By.tagName("h1")).getText(), "Hello");
        AtlassianWebDriver.quitDriver();
    }

}
