package com.atlassian.webdriver.test;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import com.google.common.collect.ImmutableMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TestHtmlUnitBug
{
    static SimpleServer server;
    static String TEST_URL;
    static AtlassianWebDriver driver;

    @BeforeClass
    public static void startServer() throws Exception
    {
        server = new SimpleServer(ImmutableMap.of("/htmlunitbug", "templates/htmlunitbug.html"));
        server.startServer();

        TEST_URL = "http://localhost:" + server.getPort() + "/";

        System.setProperty("webdriver.browser", "htmlunit");

        driver = WebDriverFactory.getDriver();
        driver.get(TEST_URL + "htmlunitbug");
    }

    @AfterClass
    public static void stopServer() throws Exception
    {
        server.stopServer();
        driver.quit();
    }

    // This test is checking that the the html unit bug has been fixed.
    // http://code.google.com/p/selenium/issues/detail?id=1280
    @Test
    public void TestHtmlUnitDoesNotFailToProcessArgs()
    {
        List<WebElement> els = driver.findElements(By.tagName("div"));
        Object[] args = new Object[] { els };
        ((JavascriptExecutor)driver).executeScript("return arguments[0] == null", args);
    }

}
