package com.atlassian.pageobjects.elements;

import com.atlassian.webdriver.utils.JavaScriptUtils;
import org.apache.commons.lang.ArrayUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.google.common.base.Preconditions.checkState;

/**
 * Executes javascript on an element
 */
final class WebDriverElementJavascript implements PageElementJavascript
{
    private final WebDriver driver;
    private final WebDriverElement element;

    private final JSMouse jsMouse = new JSMouse();
    private final JSForm jsForm = new JSForm();

    WebDriverElementJavascript(WebDriver driver, WebDriverElement element)
    {
        this.driver = driver;
        this.element = element;
    }

    public PageElementMouseJavascript mouse()
    {
        return jsMouse;
    }

    public PageElementFormJavascript form()
    {
        return jsForm;
    }


    public Object execute(String script, Object... arguments)
    {
        return getExecutor().executeScript(script, ArrayUtils.add(arguments, 0, element.waitForWebElement()));
    }

    public Object executeAsync(String script, Object... arguments)
    {
        return getExecutor().executeScript(script, ArrayUtils.add(arguments, 0, element.waitForWebElement()));
    }

    private JavascriptExecutor getExecutor()
    {
        checkState(driver instanceof JavascriptExecutor, driver + " does not support Javascript");
        return (JavascriptExecutor) driver;
    }

    private class JSMouse implements PageElementMouseJavascript
    {

        public PageElementJavascript click()
        {
            return dispatch("click");
        }

        public PageElementJavascript doubleClick()
        {
            return dispatch("dblclick");
        }

        public PageElementJavascript mouseup()
        {
            return dispatch("mouseup");
        }

        public PageElementJavascript mousedown()
        {
            return dispatch("mousedown");
        }

        public PageElementJavascript mouseover()
        {
            return dispatch("mouseover");
        }

        public PageElementJavascript mousemove()
        {
            return dispatch("mousemove");
        }

        public PageElementJavascript mouseout()
        {
            return dispatch("mouseout");
        }

    }


    private class JSForm implements PageElementFormJavascript
    {

        public PageElementJavascript select()
        {
            return dispatch("select");
        }

        public PageElementJavascript change()
        {
            return dispatch("change");
        }

        public PageElementJavascript submit()
        {
            return dispatch("submit");
        }

        public PageElementJavascript focus()
        {
            return dispatch("focus");
        }

        public PageElementJavascript blur()
        {
            return dispatch("blur");
        }
    }

    private PageElementJavascript dispatch(String eventType)
    {
        JavaScriptUtils.dispatchEvent(eventType, element.waitForWebElement(), driver);
        return this;
    }
}
