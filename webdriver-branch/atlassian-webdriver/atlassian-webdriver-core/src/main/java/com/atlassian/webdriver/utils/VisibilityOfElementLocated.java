package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Implements an ExpectedCondition that looks for the visiblity of another element.
 * The apply command will continue to execute until the element is visible.
 */
public class VisibilityOfElementLocated implements ExpectedCondition<Boolean>
{
    private final By findCondition;
    private final WebElement at;

    public VisibilityOfElementLocated(By by)
    {
        this(by, null);
    }

    public VisibilityOfElementLocated(By by, WebElement el)
    {
        this.findCondition = by;
        this.at = el;
    }

    public Boolean apply(WebDriver driver)
    {
        return Check.elementExists(findCondition, at);
    }
}