package com.atlassian.webdriver.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class JavaScriptUtils
{

    private JavaScriptUtils() {}

    public static String innerHtml(WebElement element, WebDriver driver)
    {
        return execute("return arguments[0].innerHTML", driver, element);
    }

    public static void dispatchEvent(String event, WebElement el, WebDriver driver)
    {

        // TODO: move this to a real js file and import the file if needed.
        String js = "if ( document.createEvent ) {"
                + "var eventObj = document.createEvent('MouseEvents');"
                + "eventObj.initEvent('" + event + "', true, true);"
                + "arguments[0].dispatchEvent(eventObj)"
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