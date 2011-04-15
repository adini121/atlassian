package com.atlassian.pageobjects.elements.test.query;

import com.atlassian.pageobjects.elements.query.Poller;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.query.webdriver.WebDriverQueryFactory;
import com.atlassian.pageobjects.elements.timeout.MapBasedTimeouts;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.atlassian.pageobjects.elements.query.Poller.by;
import static com.atlassian.pageobjects.elements.query.Poller.byDefaultTimeout;
import static com.atlassian.pageobjects.elements.query.Poller.now;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test case for {@link com.atlassian.pageobjects.elements.query.webdriver.WebDriverQueryFactory}.
 *
 */
public class TestWebDriverQueryFactory
{

    private Timeouts timeouts = new MapBasedTimeouts(ImmutableMap.<TimeoutType, Long>of(
            TimeoutType.DEFAULT, 500L,
            TimeoutType.EVALUATION_INTERVAL, 100L,
            TimeoutType.COMPONENT_LOAD, 200L
    ));

    @Test
    public void shouldSetUpQueryWithCustomDefaultTimeout()
    {
        WebDriver mockElementPresentDriver = mock(WebDriver.class);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockElementPresentDriver, timeouts);

        TimedQuery<Boolean> result = tested.isPresent(By.id("test"), TimeoutType.COMPONENT_LOAD);
        assertEquals(100L, result.interval());
        assertEquals(200L, result.defaultTimeout());
    }

    @Test
    public void shouldReturnValidIsPresentQuery()
    {
        WebElement mockElement = newMockElement();
        WebDriver mockElementPresentDriver = mock(WebDriver.class);
        when(mockElementPresentDriver.findElement(any(By.class))).thenReturn(mockElement);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockElementPresentDriver, timeouts);

        TimedQuery<Boolean> result = tested.isPresent(By.id("test"));
        assertEquals(100L, result.interval());
        assertEquals(500L, result.defaultTimeout());
        Poller.waitUntil(result, is(true), now());
    }

    @Test
    public void shouldReturnFalseIsPresentQuery()
    {
        WebDriver mockElementPresentDriver = mock(WebDriver.class);
        when(mockElementPresentDriver.findElement(any(By.class))).thenThrow(new NoSuchElementException("Cause you've got issues"));
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockElementPresentDriver, timeouts);

        TimedQuery<Boolean> result = tested.isPresent(By.id("test"));
        assertEquals(100L, result.interval());
        assertEquals(500L, result.defaultTimeout());
        Poller.waitUntil(result, is(false), by(1000));
    }

    @Test
    public void shouldReturnIsVisibleQueryThatIsTrueNow()
    {
        RenderedWebElement mockElement = mock(RenderedWebElement.class);
        when(mockElement.isDisplayed()).thenReturn(true);
        WebDriver mockElementPresentDriver = mock(WebDriver.class);
        when(mockElementPresentDriver.findElement(any(By.class))).thenReturn(mockElement);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockElementPresentDriver, timeouts);

        TimedQuery<Boolean> result = tested.isVisible(By.id("test"));
        assertEquals(100L, result.interval());
        assertEquals(500L, result.defaultTimeout());
        Poller.waitUntil(result, is(true), now());
    }

    @Test
    public void shouldReturnIsVisibleQueryThatIsTrueInAWhile()
    {
        RenderedWebElement mockElement = mock(RenderedWebElement.class);
        when(mockElement.isDisplayed()).thenReturn(false, false, false, false, true);
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockDriver.findElement(any(By.class))).thenReturn(mockElement);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockDriver, timeouts);

        TimedQuery<Boolean> result = tested.isVisible(By.id("test"));
        assertEquals(100L, result.interval());
        assertEquals(500L, result.defaultTimeout());
        Poller.waitUntil(result, is(false), now());
        Poller.waitUntil(result, is(true), by(1000));
    }

    @Test
    public void shouldReturnIsVisibleQueryThatIsFalse()
    {
        RenderedWebElement mockElement = mock(RenderedWebElement.class);
        when(mockElement.isDisplayed()).thenReturn(false);
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockDriver.findElement(any(By.class))).thenReturn(mockElement);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockDriver, timeouts);

        TimedQuery<Boolean> result = tested.isVisible(By.id("test"));
        assertEquals(100L, result.interval());
        assertEquals(500L, result.defaultTimeout());
        Poller.waitUntil(result, is(false), by(1000));
    }

    @Test
    public void hasClassQueryShouldReturnTrueForSimpleMatch()
    {
        RenderedWebElement mockElement = mock(RenderedWebElement.class);
        when(mockElement.getAttribute("class")).thenReturn("oneclass secondclass someotherclasssss");
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockDriver.findElement(any(By.class))).thenReturn(mockElement);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockDriver, timeouts);

        Poller.waitUntil(tested.hasClass(By.id("test"), "oneclass"), is(true), now());
        Poller.waitUntil(tested.hasClass(By.id("test"), "secondclass"), is(true), now());
        Poller.waitUntil(tested.hasClass(By.id("test"), "someotherclasssss"), is(true), now());
        Poller.waitUntil(tested.hasClass(By.id("test"), "blahblah"), is(false), byDefaultTimeout());
    }

    @Test
    public void hasClassQueryShouldBeCaseInsensitive()
    {
        RenderedWebElement mockElement = mock(RenderedWebElement.class);
        when(mockElement.getAttribute("class")).thenReturn("oneclass secOndclAss soMeotherclasSsss");
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockDriver.findElement(any(By.class))).thenReturn(mockElement);
        WebDriverQueryFactory tested = new WebDriverQueryFactory(mockDriver, timeouts);

        Poller.waitUntil(tested.hasClass(By.id("test"), "Oneclass"), is(true), now());
        Poller.waitUntil(tested.hasClass(By.id("test"), "SECondclAss"), is(true), now());
        Poller.waitUntil(tested.hasClass(By.id("test"), "someotherclaSSSSS"), is(true), now());
    }

    private WebElement newMockElement()
    {
        return mock(WebElement.class);
    }
}
