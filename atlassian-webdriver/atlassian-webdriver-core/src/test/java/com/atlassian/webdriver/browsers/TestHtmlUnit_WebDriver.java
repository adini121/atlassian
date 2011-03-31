package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserConfig;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestHtmlUnit_WebDriver extends WebDriverAutoInstallerTest
{

    private static final String HTML_UNIT_USER_AGENT = "Gecko/20100401 Firefox/3.6.3";

    @Test
    public void testHtmlUnit() throws Exception
    {
        System.setProperty("webdriver.browser", "htmlunit");

        BrowserConfig browserConfig = AutoInstallConfiguration.setupBrowser();
        AtlassianWebDriver driver = WebDriverFactory.getDriver(browserConfig);

        driver.get(TEST_URL);
        driver.waitUntilElementIsLocated(By.tagName("h1"));
        assertEquals(driver.findElement(By.tagName("h1")).getText(), "Hello");
        assertTrue(driver.findElement(By.tagName("div")).getText().contains(HTML_UNIT_USER_AGENT));
        driver.quit();

    }

}
