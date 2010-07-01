package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class MouseEvents
{

    private MouseEvents() {}

    public static WebElement hover(By by, WebDriver driver)
    {
        return hover(driver.findElement(by), driver);
    }

    public static WebElement hover(WebElement el, WebDriver driver)
    {
        JavaScriptUtils.dispatchEvent("mouseover", el, driver);

        return el;
    }

}