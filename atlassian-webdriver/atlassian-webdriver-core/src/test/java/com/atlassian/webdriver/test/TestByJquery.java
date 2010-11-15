package com.atlassian.webdriver.test;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import com.atlassian.webdriver.utils.by.ByJquery;
import com.google.common.collect.ImmutableMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for checking ByJquery functionality in Atlassian WebDriver.
 */
public class TestByJquery
{

    static SimpleServer server;
    static String TEST_URL;
    static AtlassianWebDriver driver;

    @BeforeClass
    public static void startServer() throws Exception
    {
        server = new SimpleServer(ImmutableMap.of("/byjquery", "byjquery.html"));
        server.startServer();

        TEST_URL = "http://localhost:" + server.getPort() + "/";

        System.setProperty("webdriver.browser", "htmlunit");

        driver = WebDriverFactory.getDriver();
        driver.get(TEST_URL + "byjquery");
    }

    @AfterClass
    public static void stopServer() throws Exception
    {
        server.stopServer();
        driver.quit();
    }
    
    @Test
    public void testGetHeadingByJquery()
    {
        assertEquals("By Jquery test page",driver.findElement(ByJquery.$("h1")).getText());
    }

    @Test
    public void testSimpleClassSelector()
    {
        List<WebElement> els = driver.findElements(ByJquery.$(".class1"));
        assertTrue(els.size() == 1);

        WebElement el = els.get(0);
        assertEquals("div", el.getTagName());
        assertEquals("class1", el.getAttribute("class"));
        assertEquals("Simple class test", el.getText());
    }

    @Test
    public void testSimpleIdSelector()
    {
        List<WebElement> els = driver.findElements(ByJquery.$("#id1"));
        assertTrue(els.size() == 1);

        WebElement el = els.get(0);
        assertEquals("div", el.getTagName());
        assertEquals("id1", el.getAttribute("id"));
        assertEquals("Simple ID test", el.getText());
    }

    @Test
    public void testMultipleSelector()
    {
        List<WebElement> els = driver.findElements(ByJquery.$("#id2 .innerblock"));
        assertTrue(els.size() == 1);

        WebElement el = els.get(0);
        assertEquals("div", el.getTagName());
        assertEquals("innerblock", el.getAttribute("class"));
        assertEquals("Inner block test", el.getText());
    }

    //TODO: more tests!

}
