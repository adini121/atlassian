package com.atlassian.webdriver.utils.by;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class ByHelper
{
    public final static By HTML_TAG = By.tagName("html");
    public final static By BODY_TAG = By.tagName("body");

    public static WebElement getHtml(WebDriver driver)
    {
        return driver.findElement(HTML_TAG);
    }

    public static WebElement getBody(WebDriver driver)
    {
        return driver.findElement(BODY_TAG);
    }


    private ByHelper()
    {
        throw new UnsupportedOperationException("Unable to instatiate ByHelper");
    }


}
