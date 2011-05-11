package com.atlassian.webdriver.utils;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
     *
     * @param event The name of the event to dispatch. eg. load.
     * @param el The element to fire the event on.
     * @param driver the webdriver instance that executes the javascript event.
     */
    public static void dispatchMouseEvent(String event, WebElement el, WebDriver driver)
    {

        // TODO: move this to a real js file and import the file if needed.
        String js = "if ( document.createEvent ) {"
                + "var eventObj = document.createEvent('MouseEvents');"
                + "eventObj.initEvent('" + event + "', false, true);"
                + "arguments[0].dispatchEvent(eventObj)"
                + "} else if ( document.createEventObject ) {"
                + "arguments[0].fireEvent('on" + event + "');"
                + "}";

        execute(js, driver, el);
    }

    public static void loadScript(String jsScriptName, WebDriver driver)
    {
        try
        {
            String lineSep = System.getProperty("line.separator");
            BufferedReader br = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(jsScriptName);
                if (is == null) {
                    is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsScriptName);
                }
                if (is == null) {
                    throw new RuntimeException("javascript resource " + jsScriptName + " not found by system or context classloaders.");
                }
                br = new BufferedReader(new InputStreamReader(is));
                String nextLine = "";
                StringBuffer sb = new StringBuffer();
                while ((nextLine = br.readLine()) != null)
                {
                    sb.append(nextLine);
                    //
                    // note:
                    //   BufferedReader strips the EOL character
                    //   so we add a new one!
                    //
                    sb.append(lineSep);
                }
                String jsSource = sb.toString();

                JavaScriptUtils.execute(jsSource, driver);
            } finally {
                IOUtils.closeQuietly(br);
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to load the javascript file: " + jsScriptName, e);
        }
        
    }

    public static <T> T execute(String js, WebDriver driver)
    {
        return (T) execute(js, driver, new Object[0]);
    }

    public static <T> T execute(String js, WebDriver driver, Object... arguments)
    {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (T) jsExecutor.executeScript(js, arguments);
    }


}