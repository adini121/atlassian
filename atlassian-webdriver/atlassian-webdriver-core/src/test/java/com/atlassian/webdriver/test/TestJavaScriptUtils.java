package com.atlassian.webdriver.test;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.utils.JavaScriptUtils;
import com.google.common.collect.ImmutableMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for checking ByJquery functionality in Atlassian WebDriver.
 */
public class TestJavaScriptUtils
{

    private static final String CONTEXT = "js-utils";

    private static String TEST_URL;
    private static SimpleServer server;
    private static AtlassianWebDriver driver;

    @BeforeClass
    public static void startServer() throws Exception
    {
        server = new SimpleServer(ImmutableMap.of(
                "/js-utils", "js-utils.html",
                "/js-utils.css", "js-utils.css",
                "/jquery.js", "jquery-1.4.2.js"
        ));
        server.startServer();

        TEST_URL = "http://localhost:" + server.getPort() + "/";

        driver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();
        driver.get(TEST_URL + CONTEXT);
    }

    @AfterClass
    public static void stopServer() throws Exception
    {
        server.stopServer();
        driver.quit();
    }

    @Ignore("doesn't work")
    @Test
    public void testCssHoverRespondsToMouseover()
    {
        RenderedWebElement hoveringDiv = (RenderedWebElement) driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(driver.elementIsVisible(By.id("child-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-element-three")));
        // now hover over div to show the children
        JavaScriptUtils.dispatchMouseEvent("mouseover", hoveringDiv, driver);
        assertTrue(driver.elementIsVisible(By.id("child-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-element-three")));
    }

    @Test
    public void testJQueryHoverRespondsToMouseover()
    {
        RenderedWebElement hoveringDiv = (RenderedWebElement) driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());
        driver.waitUntilElementIsNotVisible(By.id("child-jquery-element-one"));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-three")));
        // now hover over div to show the children
        JavaScriptUtils.dispatchMouseEvent("mouseover", hoveringDiv, driver);
        driver.waitUntilElementIsVisible(By.id("child-jquery-element-one"));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-three")));
    }

}
