package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.pageobjects.browser.IgnoreBrowser;
import com.atlassian.pageobjects.browser.RequireBrowser;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.JavaScriptUtilsPage;
import com.atlassian.webdriver.utils.MouseEvents;
import com.atlassian.webdriver.utils.element.ElementIsVisible;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for checking ByJquery functionality in Atlassian WebDriver.
 */
@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestJavaScriptUtils extends AbstractFileBasedServerTest
{

    public static final int TIMEOUT = 5;

    private JavaScriptUtilsPage javascriptUtilsPage;
    private AtlassianWebDriver driver;

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
    @IgnoreBrowser(value = {Browser.FIREFOX, Browser.HTMLUNIT}, reason = "CSS hovering on elements does not work.")
    public void testCssHoverRespondsToMouseover()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(driver.elementIsVisible(By.id("child-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-element-three")));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);

        driver.waitUntil(new ElementIsVisible(By.id("child-element-one")), TIMEOUT);
        assertTrue(driver.elementIsVisible(By.id("child-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-element-three")));
    }

    @Test
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

        driver.waitUntil(new ElementIsVisible(By.id("child-jquery-element-one")), TIMEOUT);
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-three")));
    }

    @Test
    @RequireBrowser(Browser.HTMLUNIT)
    public void testCssHoverBreaksForHtmlUnit()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(driver.elementIsVisible(By.id("child-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-element-three")));
        // now hover over div to show the children

        MouseEvents.hover(hoveringDiv, driver);

        try
        {
            driver.waitUntil(new ElementIsVisible(By.id("child-element-one")), TIMEOUT);
            fail("htmlunit css hovers are working now");
        }
        catch (TimeoutException expected) {}
    }

    @Test
    @RequireBrowser(Browser.FIREFOX)
    public void testCssHoverBreaksForFirefox()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(driver.elementIsVisible(By.id("child-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-element-three")));
        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);

        try
        {
            driver.waitUntil(new ElementIsVisible(By.id("child-element-one")), TIMEOUT);
            fail("firefox css hovers are working now");
        }
        catch (TimeoutException expected) {}
    }

    // https://studio.atlassian.com/browse/SELENIUM-175
    @Test
    @RequireBrowser(Browser.IE)
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
            driver.waitUntil(new ElementIsVisible(By.id("child-jquery-element-one")), TIMEOUT);
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
