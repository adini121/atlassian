package com.atlassian.pageobjects.elements.test;

import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.pageobjects.elements.GlobalElementFinder;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.PageElementFinder;
import com.atlassian.pageobjects.elements.test.pageobjects.page.EventsPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.atlassian.pageobjects.elements.DataAttributeFinder.query;
import static com.atlassian.pageobjects.elements.query.Poller.waitUntilTrue;

/**
 * Test {@link com.atlassian.pageobjects.elements.PageElementJavascript} events API.
 *
 * @since 2.1
 */
@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestPageElementJavaScriptEvents extends AbstractFileBasedServerTest
{

    private PageElementFinder elementFinder;

    @Before
    public void init()
    {
        product.visit(EventsPage.class);
        elementFinder = product.getPageBinder().bind(GlobalElementFinder.class);
    }


    @Test
    public void testMouseEvents()
    {
        final PageElement mouseEventListener = elementFinder.find(By.id("mouse-event-listener"));
        mouseEventListener.javascript().mouse().click();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "click"));
        mouseEventListener.javascript().mouse().doubleClick();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "dblclick"));
        mouseEventListener.javascript().mouse().mousedown();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "mousedown"));
        mouseEventListener.javascript().mouse().mouseup();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "mouseup"));
        mouseEventListener.javascript().mouse().mouseover();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "mouseover"));
        mouseEventListener.javascript().mouse().mousemove();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "mousemove"));
        mouseEventListener.javascript().mouse().mouseout();
        waitUntilTrue(query(mouseEventListener).timed().hasDataAttribute("event", "mouseout"));
    }

    @Test
    @IgnoreBrowser(value = {Browser.HTMLUNIT}, reason = "SELENIUM-167 :focus selector not supported")
    public void testFormEvents()
    {
        final PageElement formEventListener = elementFinder.find(By.id("form-event-listener"));
        formEventListener.javascript().form().select();
        waitUntilTrue(query(formEventListener).timed().hasDataAttribute("event", "select"));
        formEventListener.javascript().form().focus();
        waitUntilTrue(query(formEventListener).timed().hasDataAttribute("event", "focus"));
        formEventListener.javascript().form().blur();
        waitUntilTrue(query(formEventListener).timed().hasDataAttribute("event", "blur"));
        formEventListener.javascript().form().change();
        waitUntilTrue(query(formEventListener).timed().hasDataAttribute("event", "change"));
        formEventListener.javascript().form().submit();
        waitUntilTrue(query(formEventListener).timed().hasDataAttribute("event", "submit"));
    }

}
