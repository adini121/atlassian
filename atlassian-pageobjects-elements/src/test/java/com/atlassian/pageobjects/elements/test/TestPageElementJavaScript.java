package com.atlassian.pageobjects.elements.test;

import com.atlassian.pageobjects.Browser;
import com.atlassian.pageobjects.elements.GlobalElementFinder;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.PageElementFinder;
import com.atlassian.pageobjects.elements.query.Poller;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.test.pageobjects.page.ElementsPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Test {@link com.atlassian.pageobjects.elements.PageElementJavascript} events API.
 *
 * @since 2.1
 */
@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestPageElementJavaScript extends AbstractFileBasedServerTest
{

    private PageElementFinder elementFinder;

    @Before
    public void init()
    {
        product.visit(ElementsPage.class);
        elementFinder = product.getPageBinder().bind(GlobalElementFinder.class);
    }

    @Test
    public void shouldExecuteSimpleJavascript()
    {
        final PageElement delayedDiv = elementFinder.find(By.id("test1_delayedDiv"));
        final Object delayedDivPresent = delayedDiv.javascript().execute("return $(arguments[0]).length > 0");
        assertNotNull(delayedDivPresent);
        assertTrue(delayedDivPresent instanceof Boolean);
        assertTrue((Boolean) delayedDivPresent);
    }

    @Test
    public void shouldExecuteTimedScript()
    {
        final PageElement delayedDiv = elementFinder.find(By.id("test1_delayedDiv"));
        TimedQuery<Boolean> delayedSpanPresent = delayedDiv.javascript().execute("return $(arguments[0]).find('#test1_delayedSpan').length > 0", Boolean.class);
        assertFalse(delayedSpanPresent.now());
        elementFinder.find(By.id("test1_addElementsButton")).click();
        Poller.waitUntilTrue(delayedSpanPresent);
    }

    @Test
    public void shouldReturnSamePageElement()
    {
        final PageElement delayedDiv = elementFinder.find(By.id("test1_delayedDiv"));
        Object result = delayedDiv.javascript().execute("return arguments[0]");
        assertSame(delayedDiv, result);
    }

    @Test
    public void shouldReturnNewPageElement()
    {
        final PageElement delayedDiv = elementFinder.find(By.id("test1_delayedDiv"));
        Object result = delayedDiv.javascript().execute("return arguments[1]", elementFinder.find(By.id("test1_addElementsButton")));
        assertTrue(result instanceof PageElement);
        final PageElement element = (PageElement) result;
        assertEquals("test1_addElementsButton", element.getAttribute("id"));
    }
}
