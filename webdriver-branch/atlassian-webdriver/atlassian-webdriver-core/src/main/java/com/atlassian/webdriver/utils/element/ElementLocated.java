package com.atlassian.webdriver.utils.element;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Implements an ExpectedCondition that looks for the visiblity of another element.
 * The apply command will continue to execute until the element is visible.
 */
public class ElementLocated extends ElementLocationCondition
{

    public ElementLocated(By by)
    {
        super(by, Locatable.LOCATED);
    }

    public ElementLocated(By by, WebElement el)
    {
        super(by, el, Locatable.LOCATED);
    }

}