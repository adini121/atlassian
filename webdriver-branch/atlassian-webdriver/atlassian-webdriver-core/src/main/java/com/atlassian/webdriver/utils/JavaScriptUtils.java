package com.atlassian.webdriver.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * JavaScript Utilities for executing specific javascript events.
 */
public class JavaScriptUtils
{

    private JavaScriptUtils() {}

    public static String innerHtml(WebElement element, WebDriver driver)
    {
        return execute("return arguments[0].innerHTML", driver, element);
    }

    /**
     * Dispatches a javascript mouse event in the browser on a specified element
     * @param event The name of the event to dispatch. eg. load.
     * @param el The element to fire the event on.
     * @param driver the webdriver instance that executes the javascript event.
     */
    public static void dispatchMouseEvent(String event, WebElement el, WebDriver driver)
    {

        // TODO: move this to a real js file and import the file if needed.
        String js = "if ( document.createEvent ) {"
                + "var eventObj = document.createEvent('MouseEvents');"
                + "eventObj.initEvent('" + event + "', true, true);"
                + "arguments[0].dispatchMouseEvent(eventObj)"
                + "} else if ( document.createEventObject ) {"
                + "arguments[0].fireEvent('on" + event + "');"
                + "}";

        execute(js, driver, el);
    }

    private static <T> T execute(String js, WebDriver driver, Object... arguments)
    {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (T) jsExecutor.executeScript(js, arguments);
    }


}