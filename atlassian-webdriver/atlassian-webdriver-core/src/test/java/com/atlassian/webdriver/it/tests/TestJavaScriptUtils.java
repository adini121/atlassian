package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.Browser;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.JavaScriptUtilsPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import com.atlassian.webdriver.utils.MouseEvents;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;

/**
 * Test for checking ByJquery functionality in Atlassian WebDriver.
 */
@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestJavaScriptUtils extends AbstractFileBasedServerTest
{

    JavaScriptUtilsPage javascriptUtilsPage;
    AtlassianWebDriver driver;

    @Before
    public void init()
    {
        javascriptUtilsPage = product.visit(JavaScriptUtilsPage.class);
        driver = product.getTester().getDriver();
    }

    /**
     * This test is testing that css psuedo class :hover will display an element
     */
    @Test
    @IgnoreBrowser(value = {Browser.FIREFOX, Browser.HTMLUNIT, Browser.IE}, reason = "CSS hovering on elements does not work.")
    public void testCssHoverRespondsToMouseover()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(driver.elementIsVisible(By.id("child-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-element-three")));
        // now hover over div to show the children

        MouseEvents.hover(hoveringDiv, driver);

        driver.waitUntilElementIsVisible(By.id("child-element-one"));
        assertTrue(driver.elementIsVisible(By.id("child-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-element-three")));
    }

    @Test
    @IgnoreBrowser(value = {Browser.FIREFOX, Browser.HTMLUNIT, Browser.IE}, reason = "jQuery hovering on elements does not work.")
    public void testJQueryHoverRespondsToMouseover()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());

        driver.waitUntilElementIsNotVisible(By.id("child-jquery-element-one"));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-three")));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);

        driver.waitUntilElementIsVisible(By.id("child-jquery-element-one"));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-three")));
    }

    @Test
    @TestBrowser("htmlunit")
    public void testJQueryHoverBreaksForHtmlUnit()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());

        driver.waitUntilElementIsNotVisible(By.id("child-jquery-element-one"));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-three")));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);
        try
        {
            driver.waitUntilElementIsVisible(By.id("child-jquery-element-one"));
            fail("htmlunit jquery hovers are working now");
        }
        catch (TimeoutException expected) {}
    }

    @Test
    @TestBrowser("firefox")
    public void testJQueryHoverBreaksForFirefox()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());

        driver.waitUntilElementIsNotVisible(By.id("child-jquery-element-one"));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-three")));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);
        try
        {
            driver.waitUntilElementIsVisible(By.id("child-jquery-element-one"));
            fail("firefox jquery hovers are working now");
        }
        catch (TimeoutException expected) {}
    }

    // https://studio.atlassian.com/browse/SELENIUM-175
    @Test
    @TestBrowser("ie")
    public void testJQueryHoverBreaksForIE()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());

        driver.waitUntilElementIsNotVisible(By.id("child-jquery-element-one"));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-jquery-element-three")));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);
        try
        {
            driver.waitUntilElementIsVisible(By.id("child-jquery-element-one"));
            fail("ie jquery hovers are working now");
        }
        catch (TimeoutException expected) {}
    }

    @Test
    public void testMouseEventMouseOut()
    {
        WebElement mouseoutDiv = driver.findElement(By.id("mouseout-jquery-element"));
        assertTrue(mouseoutDiv.isDisplayed());

        WebElement mouseoutContainer = driver.findElement(By.id("mouseout-container"));
        assertEquals("no mouseout", mouseoutContainer.getText());

        MouseEvents.mouseout(mouseoutDiv, driver);
        assertEquals("mouseout", mouseoutContainer.getText());
        
    }

}
