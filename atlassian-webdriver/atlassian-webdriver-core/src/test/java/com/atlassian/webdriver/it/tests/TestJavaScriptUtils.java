package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.pageobjects.browser.IgnoreBrowser;
import com.atlassian.pageobjects.browser.RequireBrowser;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.JavaScriptUtilsPage;
import com.atlassian.webdriver.utils.MouseEvents;
import com.atlassian.webdriver.utils.element.WebDriverPoller;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static com.atlassian.webdriver.utils.Check.elementIsVisible;
import static com.atlassian.webdriver.utils.element.ElementConditions.isNotVisible;
import static com.atlassian.webdriver.utils.element.ElementConditions.isVisible;
import static org.junit.Assert.*;

@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestJavaScriptUtils extends AbstractFileBasedServerTest
{

    @Inject private WebDriverPoller poller;
    @Inject private WebDriver driver;

    @Before
    public void init()
    {
        product.visit(JavaScriptUtilsPage.class);
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
        assertFalse(elementIsVisible(By.id("child-element-one"), driver));
        assertFalse(elementIsVisible(By.id("child-element-two"), driver));
        assertFalse(elementIsVisible(By.id("child-element-three"), driver));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);

        poller.waitUntil(isVisible(By.id("child-element-one")));
        assertTrue(elementIsVisible(By.id("child-element-one"), driver));
        assertTrue(elementIsVisible(By.id("child-element-two"), driver));
        assertTrue(elementIsVisible(By.id("child-element-three"), driver));
    }

    @Test
    @IgnoreBrowser(value = {Browser.IE}, reason = "jQuery hovering on elements does not work.")
    public void testJQueryHoverRespondsToMouseover()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());

        poller.waitUntil(isNotVisible(By.id("child-jquery-element-one")));
        assertFalse(elementIsVisible(By.id("child-jquery-element-one"), driver));
        assertFalse(elementIsVisible(By.id("child-jquery-element-two"), driver));
        assertFalse(elementIsVisible(By.id("child-jquery-element-three"), driver));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);

        poller.waitUntil(isVisible(By.id("child-jquery-element-one")));
        assertTrue(elementIsVisible(By.id("child-jquery-element-one"), driver));
        assertTrue(elementIsVisible(By.id("child-jquery-element-two"), driver));
        assertTrue(elementIsVisible(By.id("child-jquery-element-three"), driver));
    }

    @Test(expected = TimeoutException.class)
    @RequireBrowser(Browser.HTMLUNIT)
    public void testCssHoverBreaksForHtmlUnit()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(elementIsVisible(By.id("child-element-one"), driver));
        assertFalse(elementIsVisible(By.id("child-element-two"), driver));
        assertFalse(elementIsVisible(By.id("child-element-three"), driver));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);
        poller.waitUntil(isVisible(By.id("child-element-one")), 5, TimeUnit.SECONDS);
        fail("HtmlUnit CSS hovers are working now");
    }

    @Test(expected = TimeoutException.class)
    @RequireBrowser(Browser.FIREFOX)
    public void testCssHoverBreaksForFirefox()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-element"));
        assertTrue(hoveringDiv.isDisplayed());
        assertFalse(elementIsVisible(By.id("child-element-one"), driver));
        assertFalse(elementIsVisible(By.id("child-element-two"), driver));
        assertFalse(elementIsVisible(By.id("child-element-three"), driver));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);
        poller.waitUntil(isVisible(By.id("child-element-one")), 5, TimeUnit.SECONDS);
        fail("Firefox CSS hovers are working now");
    }

    // https://ecosystemt.atlassian.net/browse/SELENIUM-175
    @Test(expected = TimeoutException.class)
    @RequireBrowser(Browser.IE)
    public void testJQueryHoverBreaksForIE()
    {
        WebElement hoveringDiv = driver.findElement(By.id("hovering-jquery-element"));
        assertTrue(hoveringDiv.isDisplayed());

        poller.waitUntil(isNotVisible(By.id("child-jquery-element-one")));
        assertFalse(elementIsVisible(By.id("child-jquery-element-one"), driver));
        assertFalse(elementIsVisible(By.id("child-jquery-element-two"), driver));
        assertFalse(elementIsVisible(By.id("child-jquery-element-three"), driver));

        // now hover over div to show the children
        MouseEvents.hover(hoveringDiv, driver);
        poller.waitUntil(isVisible(By.id("child-jquery-element-one")), 5, TimeUnit.SECONDS);
        fail("IE jquery hovers are working now");
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
