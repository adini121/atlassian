package com.atlassian.webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

/**
 * 
 */
public class TestChrome_5WebDriverAutoInstaller extends WebDriverAutoInstallerTest
{
    @Test
    public void testChrome_5() throws Exception
    {
        System.setProperty("webdriver.browser", "chrome-5");

        AtlassianWebDriver driver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();

        driver.get(TEST_URL);

        driver.waitUntilElementIsLocated(By.tagName("h1"));

        assertEquals(driver.findElement(By.tagName("h1")).getText(), "Hello");
    }

}
