package com.atlassian.pageobjects.framework.test;

import com.atlassian.pageobjects.framework.TimedQuery;
import com.atlassian.pageobjects.framework.element.TimedElement;
import com.atlassian.pageobjects.framework.test.pageobjects.page.ElementsPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.TimeoutException;

import static junit.framework.Assert.assertTrue;

public class TestTimedElement  extends AbstractFileBasedServerTest
{
    @Test
    public void testIsPresent()
    {
        product.visit(ElementsPage.class);

        // Positive - verify element that exists
        product.find(By.id("test1_addElementsButton")).timed().isPresent().waitFor(true);

        // Negative - verify element that does not exist
        product.find(By.id("non_present_element")).timed().isPresent().waitFor(false);

        // Delayed presence & Delayed positive - click on button that adds a span with delay, verify isPresent waits.
        product.find(By.id("test1_addElementsButton")).click();
        product.find(By.id("test1_delayedSpan")).timed().isPresent().waitFor(true);

        // Delayed Negative
    }

     @Test
    public void testIsVisible()
    {
        product.visit(ElementsPage.class);

        TimedElement testInput = product.find(By.id("test2_input")).timed();

        // Positive - verify input that is visible
        testInput.isVisible().waitFor(true);

        // Negative - click on button to make input invisible and verify
        product.find(By.id("test2_toggleInputVisibility")).click();
        testInput.isVisible().waitFor(false);

        // Delayed presence - click on a button that adds an element with delay, verify isVisible waits
        product.find(By.id("test2_addElementsButton")).click();
        product.find(By.id("test2_delayedSpan")).timed().isVisible().waitFor(true);

        // Delayed positive - click on button to make input visible with delay and verify
        product.find(By.id("test2_toggleInputVisibilityWithDelay")).click();
        testInput.isVisible().waitFor(true);

        // Delayed Negative
    }

     @Test
    public void testText()
    {
        product.visit(ElementsPage.class);

        // Positive - verify span with text
        product.find(By.id("test3_span")).timed().text().waitFor("Span Value");

        // Negative - verify a span that has no text
        product.find(By.id("test3_spanEmpty")).timed().text().waitFor("");

        // Delayed presence - click on button that adds a span with delay, verify getText waits
        product.find(By.id("test3_addElementsButton")).click();
        product.find(By.id("test3_delayedSpan")).timed().text().waitFor("Delayed Span");

        // Delayed postive - click on button that sets the text of span with delay, verify getText waits
        product.find(By.id("test3_setTextButton")).click();
        product.find(By.id("test3_spanEmpty")).timed().text().waitFor("Delayed Text");

        // Delayed negative
    }

    @Test
    public void testAttribute()
    {
        product.visit(ElementsPage.class);

        // Positive - verify class attribute of span
        product.find(By.id("test5_input")).timed().attribute("class").waitFor("test5-input-class");

        // Negative

        // Delayed presence - click on button that adds a span with delay, verify getAtribute waits
        product.find(By.id("test5_addElementsButton")).click();
        product.find(By.id("test5_delayedSpan")).timed().attribute("class").waitFor("test5-span-delayed");

        // Delayed positive - click on a button that adds attribute of a span, verify getAttribute waits
        product.find(By.id("test5_addAttribute")).click();
        product.find(By.id("test5_input")).timed().attribute("value").waitFor("test5-input-value");

        // Delayed negative
    }

}
