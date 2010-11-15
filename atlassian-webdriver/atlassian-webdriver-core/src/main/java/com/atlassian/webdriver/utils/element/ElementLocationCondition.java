package com.atlassian.webdriver.utils.element;

import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.by.ByHelper;
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
abstract class ElementLocationCondition implements ExpectedCondition<Boolean>
{
    enum Locatable
    {
        LOCATED,
        NOTLOCATED;
    }

    private final By by;
    private final WebElement at;
    private final Locatable locatable;

    ElementLocationCondition(By by, Locatable locatable)
    {
        this(by, null, locatable);
    }

    ElementLocationCondition(By by, WebElement at, Locatable locatable)
    {
        Validate.notNull(by, "by cannot be null.");

        this.by = by;
        this.at = at;
        this.locatable = locatable;
    }

    public Boolean apply(WebDriver webDriver)
    {
        if (locatable.equals(Locatable.LOCATED))
        {
            return Check.elementExists(by, at == null ? webDriver : at);
        }
        else
        {
            return !Check.elementExists(by, at == null ? webDriver : at);
        }
    }
}