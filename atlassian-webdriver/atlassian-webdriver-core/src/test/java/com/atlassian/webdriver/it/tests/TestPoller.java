package com.atlassian.webdriver.it.tests;

import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.PollerPage;
import com.atlassian.webdriver.poller.Poller;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.utils.Check;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @since 2.1.0
 */
public class TestPoller extends AbstractFileBasedServerTest
{

    PollerPage pollerPage;
    Poller poller;
    WebDriver driver;

    ConditionFunction falseFunc = new ConditionFunction()
    {
        public Boolean apply(WebDriver from)
        {
            return false;
        }
    };

    @Before
    public void setup()
    {
        pollerPage = product.visit(PollerPage.class);
        poller = pollerPage.getPoller();
        driver = product.getTester().getDriver();
    }

    @Test
    public void testFunctionPollerIsTrue()
    {
        assertEquals("Title was not what it was expected to be", "JavaScriptUtils test page",
                driver.getTitle());

        poller.until("10ms").function(new ConditionFunction() {
            public Boolean apply(WebDriver from)
            {
                return from.getTitle().equals("JavaScriptUtils test page");
            }
        }).isTrue().execute();
    }

    @Test
    public void testFunctionPollerIsFalse()
    {
        assertEquals("Title was not what it was expected to be", "JavaScriptUtils test page",
                driver.getTitle());
        Assert.assertNotSame("Not real title", driver.getTitle());
        poller.until("10ms").function(new ConditionFunction() {
            public Boolean apply(WebDriver from)
            {
                return from.getTitle().equals("Not real title");
            }
        }).isFalse().execute();
    }

    @Test
    public void testWebElementIsVisible()
    {
        WebElement showEl = driver.findElement(By.id("dialog-one-show-button"));
        WebElement el = driver.findElement(By.id("dialog-one"));
        assertFalse(el.isDisplayed());
        showEl.click();

        poller.until(1).element(el).isVisible().execute();

        assertTrue(el.isDisplayed());
    }

    @Test
    public void testWebElementWithByLocatorIsVisible()
    {
        WebElement showEl = driver.findElement(By.id("dialog-one-show-button"));
        WebElement el = driver.findElement(By.id("dialog-one"));
        assertFalse(el.isDisplayed());
        showEl.click();

        poller.until(1).element(By.id("dialog-one")).isVisible().execute();

        assertTrue(el.isDisplayed());
    }

    @Test
    public void testWebElementWithByLocatorAndContextIsVisible()
    {
        WebElement showEl = driver.findElement(By.id("dialog-one-show-button"));
        WebElement container = driver.findElement(By.id("dialog-one-container"));
        WebElement el = container.findElement(By.id("dialog-one"));

        assertFalse(el.isDisplayed());
        showEl.click();

        poller.until(1).element(By.id("dialog-one"), container).isVisible().execute();

        assertTrue(el.isDisplayed());

    }

    @Test
    public void testWebElementIsNotVisible()
    {
        WebElement hideEl = driver.findElement(By.id("dialog-two-hide-button"));
        WebElement el = driver.findElement(By.id("dialog-two"));

        assertTrue(el.isDisplayed());
        hideEl.click();

        poller.until(1).element(el).isNotVisible().execute();

        assertFalse(el.isDisplayed());
    }

    @Test
    public void testWebElementExists()
    {
        WebElement createEl = driver.findElement(By.id("dialog-three-create-button"));
        try
        {
            driver.findElement(By.id("dialog-three"));
            fail("The web element was not supposed to exist");
        }
        catch (NoSuchElementException e) {}

        createEl.click();

        poller.until(1).element(By.id("dialog-three")).exists().execute();
        
        assertTrue(driver.findElement(By.id("dialog-three")).isDisplayed());
    }

    @Test
    public void testWebElementNotExists()
    {
        WebElement removeEl = driver.findElement(By.id("dialog-four-remove-button"));
        WebElement el = driver.findElement(By.id("dialog-four"));

        removeEl.click();

        poller.until(1).element(By.id("dialog-four")).doesNotExist().execute();

        try
        {
            driver.findElement(By.id("dialog-four"));
            fail("The web element was not supposed to exist");
        }
        catch (NoSuchElementException e) {}

    }

    @Test
    public void testWebElementIsSelected()
    {
        WebElement checkEl = driver.findElement(By.id("dialog-five-select-button"));
        WebElement el = driver.findElement(By.id("dialog-five"));

        assertFalse(el.isSelected());

        checkEl.click();

        poller.until(1).element(el).isSelected().execute();

        assertTrue(el.isSelected());
    }

    @Test
    public void testWebElementIsNotSelected()
    {
        WebElement unCheckEl = driver.findElement(By.id("dialog-six-select-button"));
        WebElement el = driver.findElement(By.id("dialog-six"));

        assertTrue(el.isSelected());

        unCheckEl.click();

        poller.until(1).element(el).isNotSelected().execute();

        assertFalse(el.isSelected());
    }

    @Test
    public void testWebElementIsEnabled()
    {
        WebElement enableEl = driver.findElement(By.id("dialog-seven-enable-button"));
        WebElement el = driver.findElement(By.id("dialog-seven"));

        assertFalse(el.isEnabled());

        enableEl.click();

        poller.until(1).element(el).isEnabled().execute();

        assertTrue(el.isEnabled());
    }

    @Test
    public void testWebElementIsNotEnabled()
    {
        WebElement disableEl = driver.findElement(By.id("dialog-eight-disable-button"));
        WebElement el = driver.findElement(By.id("dialog-eight"));

        assertTrue(el.isEnabled());

        disableEl.click();

        poller.until(1).element(el).isNotEnabled().execute();

        assertFalse(el.isEnabled());
    }

    @Test
    public void testWebElementHasClass()
    {
        WebElement addClassEl = driver.findElement(By.id("dialog-nine-addclass-button"));
        WebElement el = driver.findElement(By.id("dialog-nine"));

        assertFalse(Check.hasClass("awesome", el));

        addClassEl.click();

        poller.until(1).element(el).hasClass("awesome").execute();

        assertTrue(Check.hasClass("awesome", el));
    }

    @Test
    public void testWebElementDoesNotHaveClass()
    {
        WebElement removeClassEl = driver.findElement(By.id("dialog-ten-removeclass-button"));
        WebElement el = driver.findElement(By.id("dialog-ten"));

        assertTrue(Check.hasClass("awesome", el));

        removeClassEl.click();

        poller.until(1).element(el).doesNotHaveClass("awesome").execute();

        assertFalse(Check.hasClass("awesome", el));
    }

    @Test
    public void testWebElementGetText()
    {
        WebElement addTextEl = driver.findElement(By.id("dialog-eleven-addtext-button"));
        WebElement el = driver.findElement(By.id("dialog-eleven"));

        assertTrue(el.getText().isEmpty());

        addTextEl.click();

        poller.until(1).element(el).getText().isNotEmpty().execute();

        assertFalse(el.getText().isEmpty());
        assertEquals("Element text did not match expected.", "Dialog eleven", el.getText());
    }

    @Test
    public void testWebElementGetAttribute()
    {
        WebElement addAttrEl = driver.findElement(By.id("dialog-twelve-addattribute-button"));
        WebElement el = driver.findElement(By.id("dialog-twelve"));

        assertNull(el.getAttribute("data-test"));

        addAttrEl.click();

        poller.until(1).element(el).getAttribute("data-test").isNotEmpty().execute();

        assertFalse(el.getAttribute("data-test").isEmpty());
        assertEquals("Element attribute data-test did not match expected.",
                "value", el.getAttribute("data-test"));
    }

    @Test(expected = org.openqa.selenium.TimeoutException.class)
    public void testPollerFailure()
    {
        poller.until(1).function(new ConditionFunction() {
            public Boolean apply(WebDriver from)
            {
                return false;
            }
        }).isTrue().execute();
    }

    @Test
    public void testAndPollerQuery()
    {
        WebElement addClassAndTextEl =
                driver.findElement(By.id("dialog-thirteen-addattributeandclass-button"));
        WebElement el = driver.findElement(By.id("dialog-thirteen"));

        assertFalse(Check.hasClass("awesome", el));
        assertTrue(el.getText().isEmpty());

        addClassAndTextEl.click();

        poller.until("1500ms").element(el).hasClass("awesome").and()
                .element(el).getText().isNotEmpty().execute();

        assertTrue(Check.hasClass("awesome", el));
        assertFalse(el.getText().isEmpty());
        assertEquals("element text was not what was expected.", "Dialog thirteen", el.getText());
    }

    @Test
    public void testOrPolleryQuery()
    {
        WebElement addClassEl = driver.findElement(By.id("dialog-nine-addclass-button"));
        WebElement el = driver.findElement(By.id("dialog-nine"));

        assertFalse(Check.hasClass("awesome", el));

        addClassEl.click();

        poller.until(1)
                .function(falseFunc).isTrue().or()
                .element(el).hasClass("awesome").execute();

        assertTrue(Check.hasClass("awesome", el));
    }

    @Test
    public void testAndOrPollerQuery()
    {
        WebElement addClassEl = driver.findElement(By.id("dialog-nine-addclass-button"));
        WebElement el = driver.findElement(By.id("dialog-nine"));

        assertFalse(Check.hasClass("awesome", el));

        addClassEl.click();

        poller.until(1)
            .function(falseFunc).isFalse().and()
            .function(falseFunc).isTrue().or()
            .element(el).hasClass("awesome").execute();

        assertTrue(Check.hasClass("awesome", el));
    }

    @Test
    public void testOrAndPolleryQuery()
    {
        WebElement addClassAndTextEl =
                driver.findElement(By.id("dialog-thirteen-addattributeandclass-button"));
        WebElement el = driver.findElement(By.id("dialog-thirteen"));

        assertFalse(Check.hasClass("awesome", el));
        assertTrue(el.getText().isEmpty());

        addClassAndTextEl.click();

        poller.until("1500ms")
                .function(falseFunc).isTrue().or()
                .element(el).hasClass("awesome").and()
                .element(el).getText().isNotEmpty().execute();

        assertTrue(Check.hasClass("awesome", el));
        assertFalse(el.getText().isEmpty());
        assertEquals("element text was not what was expected.", "Dialog thirteen", el.getText());
    }

}
