package it.com.atlassian.webdriver.tests;

import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.element.Element;
import it.com.atlassian.webdriver.pageobjects.RefappTestedProduct;
import it.com.atlassian.webdriver.pageobjects.page.RefappHtmlElementPage;
import org.junit.Test;
import org.openqa.selenium.By;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TestDelayedElement
{
    private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class);

    @Test
    public void testFieldInjection()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);

        // click on a button that was injected via @DelayedBy
        htmlPage.test1_addElementsButton().click();

        // verify delayed element that was injected via @DelayedBy waits
        assertTrue(htmlPage.test1_delayedSpan().timed().isPresent().checkFor(true));
    }

     @Test
    public void testIsPresent()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        // Positive - verify element that exists
        assertTrue(driver.findDelayed(By.id("test1_addElementsButton")).isPresent());

        // Negative - verify element that does not exist
        assertFalse(driver.findDelayed(By.id("test1_non_existant")).isPresent());

        // Delayed presence - click on button that adds a span with delay, verify isPresent does not wait.
        driver.findDelayed(By.id("test1_addElementsButton")).click();
        assertFalse(driver.findDelayed(By.id("test1_delayedSpan")).isPresent());
    }

    @Test
    public void testIsVisible()
    {
       RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
       AtlassianWebDriver driver = REFAPP.getTester().getDriver();

       Element testInput = driver.findDelayed(By.id("test2_input"));

       // Positive - verify input that is visible
       assertTrue(testInput.isVisible());

       // Delayed presence - click on a button that adds an element with delay, verify isVisible waits
       driver.findDelayed(By.id("test2_addElementsButton")).click();
       assertTrue(driver.findDelayed(By.id("test2_delayedSpan")).isVisible());

        // Delayed positive - click on button to make input visible with delay and verify that it did not wait
        driver.findDelayed(By.id("test2_toggleInputVisibility")).click();
        driver.findDelayed(By.id("test2_toggleInputVisibilityWithDelay")).click();
        assertFalse(testInput.isVisible());

    }

    @Test
    public void testText()
    {
        RefappHtmlElementPage htmlPage = REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        // Positive - verify span with text
        assertEquals("Span Value", driver.findDelayed(By.id("test3_span")).text());

        // Delayed presence - click on button that adds a span with delay, verify getText waits
        driver.findDelayed(By.id("test3_addElementsButton")).click();
        assertEquals("Delayed Span", driver.findDelayed(By.id("test3_delayedSpan")).text());

        // Delayed postive - click on button that sets the text of span with delay, verify getText does not wait
        driver.findDelayed(By.id("test3_setTextButton")).click();
        assertEquals("", driver.findDelayed(By.id("test3_spanEmpty")).text());
    }

    @Test
    public void testfindDelayed()
    {
        REFAPP.visit(RefappHtmlElementPage.class);
        AtlassianWebDriver driver = REFAPP.getTester().getDriver();

        // find a delayed elements within another
        Element childList = driver.findDelayed(By.id("test4_parentList")).findDelayed(By.tagName("ul"));
        assertEquals("test4_childList", childList.attribute("id"));

        Element leafList = childList.findDelayed(By.tagName("ul"));
        assertEquals("test4_leafList", leafList.attribute("id"));

        Element listItem = leafList.findDelayed(By.tagName("li"));
        assertEquals("Item 1", listItem.text());

        //wait for presence on an element within another
        driver.findDelayed(By.id("test4_addElementsButton")).click();
        assertTrue(leafList.findDelayed(By.linkText("Item 4")).timed().isPresent().checkFor(true));

        //wait for text on an element within another
        REFAPP.visit(RefappHtmlElementPage.class);

        driver.findDelayed(By.id("test4_addElementsButton")).click();
        assertEquals("Item 5", driver.findDelayed(By.className("listitem-active")).text());
    }
}
