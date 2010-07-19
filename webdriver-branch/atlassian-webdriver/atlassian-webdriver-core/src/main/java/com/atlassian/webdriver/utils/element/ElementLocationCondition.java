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
class ElementLocationCondition implements ExpectedCondition<Boolean>
{
    enum Locatable
    {
        LOCATED,
        NOTLOCATED;
    }

    private final By findCondition;
    private final WebElement at;
    private final Locatable locatable;

    ElementLocationCondition(By findCondition, Locatable locatable)
    {
        this(findCondition, AtlassianWebDriver.getBody(), locatable);
    }

    ElementLocationCondition(By findCondition, WebElement at, Locatable locatable)
    {
        Validate.notNull(findCondition, "find condition cannot be null.");
        Validate.notNull(at, "element location condition 'at' cannot be null.");

        this.findCondition = findCondition;
        this.at = at;
        this.locatable = locatable;
    }

    public Boolean apply(WebDriver webDriver)
    {
        if (locatable.equals(Locatable.LOCATED))
        {
            return Check.elementExists(findCondition, at);
        }
        else
        {
            return !Check.elementExists(findCondition, at);
        }
    }
}
