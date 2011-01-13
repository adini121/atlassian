package it.com.atlassian.webdriver.tests;

import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.element.TimedElement;
import it.com.atlassian.webdriver.pageobjects.RefappTestedProduct;
import it.com.atlassian.webdriver.pageobjects.page.RefappHtmlElementPage;
import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TestTimedElement
{
     private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class);

     @Test
    public void testIsPresent()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        // Positive - verify element that exists
        assertTrue(driver.find(By.id("test1_addElementsButton")).timed().isPresent().checkFor(true));

        // Negative - verify element that does not exist
        assertTrue(driver.find(By.id("non_present_element")).timed().isPresent().checkFor(false));

        // Delayed presence & Delayed positive - click on button that adds a span with delay, verify isPresent waits.
        driver.find(By.id("test1_addElementsButton")).click();
        assertTrue(driver.find(By.id("test1_delayedSpan")).timed().isPresent().checkFor(true));

        // Delayed Negative
    }

    @Test
    public void testIsVisible()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        TimedElement testInput = driver.find(By.id("test2_input")).timed();

        // Positive - verify input that is visible
        assertTrue(testInput.isVisible().checkFor(true));

        // Negative - click on button to make input invisible and verify
        driver.find(By.id("test2_toggleInputVisibility")).click();
        assertTrue(testInput.isVisible().checkFor(false));

        // Delayed presence - click on a button that adds an element with delay, verify isVisible waits
        driver.find(By.id("test2_addElementsButton")).click();
        assertTrue(driver.find(By.id("test2_delayedSpan")).timed().isVisible().checkFor(true));

        // Delayed positive - click on button to make input visible with delay and verify
        driver.find(By.id("test2_toggleInputVisibilityWithDelay")).click();
        assertTrue(testInput.isVisible().checkFor(true));

        // Delayed Negative
    }

     @Test
    public void testText()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        // Positive - verify span with text
        assertTrue(driver.find(By.id("test3_span")).timed().text().checkFor("Span Value"));

        // Negative - verify a span that has no text
        assertTrue(driver.find(By.id("test3_spanEmpty")).timed().text().checkFor(""));

        // Delayed presence - click on button that adds a span with delay, verify getText waits
        driver.find(By.id("test3_addElementsButton")).click();
        assertTrue(driver.find(By.id("test3_delayedSpan")).timed().text().checkFor("Delayed Span"));

        // Delayed postive - click on button that sets the text of span with delay, verify getText waits
        driver.find(By.id("test3_setTextButton")).click();
        assertTrue(driver.find(By.id("test3_spanEmpty")).timed().text().checkFor("Delayed Text"));

        // Delayed negative
    }

    @Test
    public void testAttribute()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        // Positive - verify class attribute of span
        assertTrue(driver.find(By.id("test5_input")).timed().attribute("class").checkFor("test5-input-class"));

        // Negative

        // Delayed presence - click on button that adds a span with delay, verify getAtribute waits
        driver.find(By.id("test5_addElementsButton")).click();
        assertTrue(driver.find(By.id("test5_delayedSpan")).timed().attribute("class").checkFor("test5-span-delayed"));

        // Delayed positive - click on a button that adds attribute of a span, verify getAttribute waits
        driver.find(By.id("test5_addAttribute")).click();
        assertTrue(driver.find(By.id("test5_input")).timed().attribute("value").checkFor("test5-input-value"));

        // Delayed negative
    }
    
}
