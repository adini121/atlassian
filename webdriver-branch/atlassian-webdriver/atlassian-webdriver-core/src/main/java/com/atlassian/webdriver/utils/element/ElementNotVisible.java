package com.atlassian.webdriver.utils.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ElementNotVisible extends ElementVisibilityCondition
{
    public ElementNotVisible(By by)
    {
        super(by, Visibility.NOTVISIBLE);
    }

    public ElementNotVisible(By by, WebElement el)
    {
        super(by, el, Visibility.NOTVISIBLE);
    }
}