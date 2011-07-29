package com.atlassian.webdriver.it.tests;

import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.JavaScriptUtilsPage;
import com.atlassian.webdriver.utils.Browser;
import com.atlassian.webdriver.utils.MouseEvents;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test for checking ByJquery functionality in Atlassian WebDriver.
 */
public class TestJavaScriptUtils extends AbstractFileBasedServerTest
{

    JavaScriptUtilsPage javascriptUtilsPage;

    @Before
    public void init()
    {
        javascriptUtilsPage = product.getPageBinder().navigateToAndBind(JavaScriptUtilsPage.class);
    }

    /**
     * This test is testing that css psuedo class :hover will display an element
     */
    @Test
    @IgnoreBrowser (value = {Browser.FIREFOX, Browser.HTMLUNIT}, reason = "CSS hovering on elements does not work.")
    public void testCssHoverRespondsToMouseover()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(driver.elementIsVisible(By.id("child-element-one")));
        assertFalse(driver.elementIsVisible(By.id("child-element-two")));
        assertFalse(driver.elementIsVisible(By.id("child-element-three")));
        // now hover over div to show the children

        MouseEvents.hover(hoveringDiv, driver);

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

        driver.waitUntilElementIsVisible(By.id("child-jquery-element-one"));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-one")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-two")));
        assertTrue(driver.elementIsVisible(By.id("child-jquery-element-three")));
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
