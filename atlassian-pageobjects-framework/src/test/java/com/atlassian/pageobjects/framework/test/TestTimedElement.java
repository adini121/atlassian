package com.atlassian.pageobjects.framework.test;

import com.atlassian.pageobjects.framework.element.TimedElement;
import com.atlassian.pageobjects.framework.test.pageobjects.page.ElementsPage;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertEqualsByDefaultTimeout;
import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertFalseByDefaultTimeout;
import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertThatByDefaultTimeout;
import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertTrueByDefaultTimeout;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class TestTimedElement extends AbstractFileBasedServerTest
{
    @Test
    public void testIsPresent()
    {
        product.visit(ElementsPage.class);

        // Positive - verify element that exists
        assertTrueByDefaultTimeout(product.find(By.id("test1_addElementsButton")).timed().isPresent());

        // Negative - verify element that does not exist
        assertFalseByDefaultTimeout(product.find(By.id("non_present_element")).timed().isPresent());

        // Delayed presence & Delayed positive - click on button that adds a span with delay, verify isPresent waits.
        product.find(By.id("test1_addElementsButton")).click();
        assertTrueByDefaultTimeout(product.find(By.id("test1_delayedSpan")).timed().isPresent());

        // Delayed Negative
    }

    @Test
    public void testIsVisible()
    {
        product.visit(ElementsPage.class);

        TimedElement testInput = product.find(By.id("test2_input")).timed();

        // Positive - verify input that is visible
        assertTrueByDefaultTimeout(testInput.isVisible());

        // Negative - click on button to make input invisible and verify
        product.find(By.id("test2_toggleInputVisibility")).click();
        assertFalseByDefaultTimeout(testInput.isVisible());

        // Delayed presence - click on a button that adds an element with delay, verify isVisible waits
        product.find(By.id("test2_addElementsButton")).click();
        assertTrueByDefaultTimeout(product.find(By.id("test2_delayedSpan")).timed().isVisible());

        // Delayed positive - click on button to make input visible with delay and verify
        product.find(By.id("test2_toggleInputVisibilityWithDelay")).click();
        assertTrueByDefaultTimeout(testInput.isVisible());

        // Delayed Negative
    }

    @Test
    public void testText()
    {
        product.visit(ElementsPage.class);

        // Positive - verify span with text
        assertEqualsByDefaultTimeout("Span Value", product.find(By.id("test3_span")).timed().text());

        // check non-case-sensitive
        assertThatByDefaultTimeout(product.find(By.id("test3_span")).timed().text(), equalToIgnoringCase("span value"));

        // Negative - verify a span that has no text
        assertEqualsByDefaultTimeout("", product.find(By.id("test3_spanEmpty")).timed().text());

        // Delayed presence - click on button that adds a span with delay, verify getText waits
        product.find(By.id("test3_addElementsButton")).click();
        assertEqualsByDefaultTimeout("Delayed Span", product.find(By.id("test3_delayedSpan")).timed().text());

        // Delayed postive - click on button that sets the text of span with delay, verify getText waits
        product.find(By.id("test3_setTextButton")).click();
        assertEqualsByDefaultTimeout("Delayed Text", product.find(By.id("test3_spanEmpty")).timed().text());

        // Delayed negative
    }

    @Test
    public void testAttribute()
    {
        product.visit(ElementsPage.class);

        // Positive - verify class attribute of span
        assertEqualsByDefaultTimeout("test5-input-class", product.find(By.id("test5_input")).timed().attribute("class"));

        // Negative

        // Delayed presence - click on button that adds a span with delay, verify getAtribute waits
        product.find(By.id("test5_addElementsButton")).click();
        assertEqualsByDefaultTimeout("test5-span-delayed", product.find(By.id("test5_delayedSpan")).timed().attribute("class"));

        // Delayed positive - click on a button that adds attribute of a span, verify getAttribute waits
        product.find(By.id("test5_addAttribute")).click();
        assertEqualsByDefaultTimeout("test5-input-value", product.find(By.id("test5_input")).timed().attribute("value"));

        // Delayed negative
    }

}
