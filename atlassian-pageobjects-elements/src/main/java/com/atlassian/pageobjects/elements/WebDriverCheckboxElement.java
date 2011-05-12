package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Implementation of (@Link CheckboxElement)
 */
public class WebDriverCheckboxElement extends WebDriverElement implements  CheckboxElement
{
    public WebDriverCheckboxElement(By locator)
    {
        super(locator);
    }

    public WebDriverCheckboxElement(By locator, TimeoutType defaultTimeout)
    {
        super(locator, defaultTimeout);
    }

    public WebDriverCheckboxElement(WebDriverLocatable locatable, TimeoutType timeoutType)
    {
        super(locatable, timeoutType);
    }

    public WebDriverCheckboxElement(By locator, WebDriverLocatable parent)
    {
        super(locator, parent);
    }

    public CheckboxElement check()
    {
        select();
        return this;
    }

    public CheckboxElement uncheck()
    {
        if(isSelected())
        {
            toggle();
        }
        return this;
    }
}
