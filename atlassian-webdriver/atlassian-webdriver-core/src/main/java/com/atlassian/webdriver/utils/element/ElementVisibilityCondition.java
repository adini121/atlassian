package com.atlassian.webdriver.utils.element;

import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.by.ByHelper;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
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

    private final By by;
    private final SearchContext at;
    private final Visibility visibility;

    ElementVisibilityCondition(By by, Visibility visibility)
    {
        this(by, null, visibility);
    }

    ElementVisibilityCondition(By by, SearchContext el, Visibility visibility)
    {
        Validate.notNull(by, "by cannot be null.");

        this.by = by;
        this.at = el;
        this.visibility = visibility;
    }

    final public Boolean apply(WebDriver driver)
    {
        if (visibility.equals(Visibility.VISIBLE))
        {
            return Check.elementIsVisible(by, at);
        }
        else
        {
            return !Check.elementIsVisible(by, at);
        }
    }
}