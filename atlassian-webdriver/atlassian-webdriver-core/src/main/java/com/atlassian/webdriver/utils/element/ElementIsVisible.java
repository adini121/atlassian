package com.atlassian.webdriver.utils.element;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ElementIsVisible extends ElementVisibilityCondition
{

    public ElementIsVisible(By by)
    {
        super(by, Visibility.VISIBLE);
    }

    public ElementIsVisible(By by, SearchContext el)
    {
        super(by, el, Visibility.VISIBLE);
    }
}
