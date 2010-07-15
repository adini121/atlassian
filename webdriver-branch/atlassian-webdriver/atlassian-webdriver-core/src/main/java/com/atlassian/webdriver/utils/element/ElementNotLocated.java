package com.atlassian.webdriver.utils.element;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Continues to execute until a particular element is no longer located.
 */
public class ElementNotLocated extends ElementLocationCondition
{

    public ElementNotLocated(By by)
    {
        super(by, Locatable.NOTLOCATED);
    }

    public ElementNotLocated(By by, WebElement el)
    {
        super(by, Locatable.NOTLOCATED);
    }

}