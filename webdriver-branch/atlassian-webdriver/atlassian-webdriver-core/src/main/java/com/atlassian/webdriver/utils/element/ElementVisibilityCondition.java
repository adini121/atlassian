package com.atlassian.webdriver.utils.element;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
abstract class ElementVisibilityCondition implements ExpectedCondition<Boolean>
{

    enum Visibility
    {
        VISIBLE,
        NOTVISIBLE;
    }

    private final By findCondition;
    private final WebElement at;
    private final Visibility visibility;

    ElementVisibilityCondition(By by, Visibility visibility)
    {
        this(by, AtlassianWebDriver.getBody(), visibility);
    }

    ElementVisibilityCondition(By by, WebElement el, Visibility visibility)
    {
        Validate.notNull(el,"WebElement cannot be null");

        this.findCondition = by;
        this.at = el;
        this.visibility = visibility;
    }
    
    final public Boolean apply(WebDriver driver)
    {
        if (visibility.equals(Visibility.VISIBLE))
        {
            return Check.elementIsVisible(findCondition, at);
        }
        else
        {
            return !Check.elementIsVisible(findCondition, at);
        }
    }
}
