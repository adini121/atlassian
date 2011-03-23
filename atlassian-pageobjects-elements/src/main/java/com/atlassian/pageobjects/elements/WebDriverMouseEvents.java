package com.atlassian.pageobjects.elements;

import com.atlassian.webdriver.utils.JavaScriptUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Allows dispatching individual mouse events to an element
 */
public class WebDriverMouseEvents
{
    private final WebDriver driver;
    private final WebElement element;

    /**
     * Creates a WebDriverMouseEvents object for the given element
     * @param driver The WebDriver instance
     * @param element The WebElement to dispatch events to.
     */
    public WebDriverMouseEvents(WebDriver driver, WebElement element)
    {
        this.driver = driver;
        this.element = element;
    }

    /**
     * Dispatches a hover event to this element
     */
    public void hover()
    {
        JavaScriptUtils.dispatchMouseEvent("mouseover", element, driver);
    }

    /**
     * Dispatches a click event to this element
     */
    public void click()
    {
        JavaScriptUtils.dispatchMouseEvent("click", element, driver);
    }
}
