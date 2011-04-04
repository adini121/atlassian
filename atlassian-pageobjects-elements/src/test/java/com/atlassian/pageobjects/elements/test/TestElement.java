package com.atlassian.pageobjects.elements.test;

import com.atlassian.pageobjects.elements.Element;
import com.atlassian.pageobjects.elements.test.pageobjects.page.ElementsPage;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.atlassian.pageobjects.elements.query.TimedAssertions.assertTrueByDefaultTimeout;
import static junit.framework.Assert.*;

public class TestElement extends AbstractFileBasedServerTest
{
    @Test
    public void testFieldInjection()
   {
       ElementsPage elementsPage = product.visit(ElementsPage.class);

       // click on a button that was injected via @ElementBy
       elementsPage.test1_addElementsButton().click();

       // verify delayed element that was injected via @ElementBy waits
       assertTrueByDefaultTimeout(elementsPage.test1_delayedSpan().timed().isPresent());
   }


    @Test
    public void testIsPresent()
    {
        product.visit(ElementsPage.class);

        // Positive - verify element that exists
        assertTrue(product.find(By.id("test1_addElementsButton")).isPresent());

        // Negative - verify element that does not exist
        assertFalse(product.find(By.id("test1_non_existant")).isPresent());

        // Delayed presence - click on button that adds a span with delay, verify isPresent does not wait.
        product.find(By.id("test1_addElementsButton")).click();
        assertFalse(product.find(By.id("test1_delayedSpan")).isPresent());
    }

    @Test
    public void testIsVisible()
    {
        product.visit(ElementsPage.class);

       Element testInput = product.find(By.id("test2_input"));

       // Positive - verify input that is visible
       assertTrue(testInput.isVisible());

       // Delayed presence - click on a button that adds an element with delay, verify isVisible waits
       product.find(By.id("test2_addElementsButton")).click();
       assertTrue(product.find(By.id("test2_delayedSpan")).isVisible());

        // Delayed positive - click on button to make input visible with delay and verify that it did not wait
        product.find(By.id("test2_toggleInputVisibility")).click();
        product.find(By.id("test2_toggleInputVisibilityWithDelay")).click();
        assertFalse(testInput.isVisible());
    }


    @Test
    public void testGetText()
    {
        product.visit(ElementsPage.class);

        // Positive - verify span with text
        assertEquals("Span Value", product.find(By.id("test3_span")).getText());

        // Delayed presence - click on button that adds a span with delay, verify getText waits
        product.find(By.id("test3_addElementsButton")).click();
        assertEquals("Delayed Span", product.find(By.id("test3_delayedSpan")).getText());

        // Delayed postive - click on button that sets the text of span with delay, verify getText does not wait
        product.find(By.id("test3_setTextButton")).click();
        assertEquals("", product.find(By.id("test3_spanEmpty")).getText());
    }

    @Test
    public void testFind()
    {
        product.visit(ElementsPage.class);

        // find a delayed elements within another
        Element childList = product.find(By.id("test4_parentList")).find(By.tagName("ul"));
        assertEquals("test4_childList", childList.getAttribute("id"));

        Element leafList = childList.find(By.tagName("ul"));
        assertEquals("test4_leafList", leafList.getAttribute("id"));

        Element listItem = leafList.find(By.tagName("li"));
        assertEquals("Item 1", listItem.getText());

        //wait for presence on an element within another
        product.find(By.id("test4_addElementsButton")).click();
        assertTrueByDefaultTimeout(leafList.find(By.linkText("Item 4")).timed().isPresent());

        //wait for text on an element within another
        driver.get(rootUrl + "/html/elements.html");

        product.find(By.id("test4_addElementsButton")).click();
        assertEquals("Item 5", product.find(By.className("listitem-active")).getText());
    }

    @Test
    public void testGetTagName()
    {
        ElementsPage elementsPage = product.visit(ElementsPage.class);

        assertEquals("input", elementsPage.test1_addElementsButton().getTagName());
    }

    @Test
    public void testHasAttribute()
    {
        ElementsPage elementsPage = product.visit(ElementsPage.class);

        // positive
        assertTrue(elementsPage.test1_addElementsButton().hasAttribute("type", "button"));

        // incorrect attribute
        assertFalse(elementsPage.test1_addElementsButton().hasAttribute("type", "bar"));

        // attribute not present
        assertFalse(elementsPage.test1_addElementsButton().hasAttribute("nonexistant", "foo"));
    }
}
