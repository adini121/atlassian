package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class VisibilityOfElementNotLocated implements ExpectedCondition<Boolean>
{

    private final By findCondition;
    private final WebElement at;

    public VisibilityOfElementNotLocated(By by)
    {
        this(by, null);
    }

    public VisibilityOfElementNotLocated(By by, WebElement el)
    {
        this.findCondition = by;
        this.at = el;
    }

    public Boolean apply(WebDriver driver)
    {
        if (Check.elementExists(findCondition, at)) {
            return false;
        } else {
            return true;
        }
    }

}