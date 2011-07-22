package com.atlassian.pageobjects.elements.query.webdriver;

import com.atlassian.webdriver.utils.Check;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Collection of functions for implementing timed queries in WebDriver.
 *
 */
public final class WebDriverQueryFunctions
{
    private static final String CLASS_ATTR_NAME = "class";

    private WebDriverQueryFunctions()
    {
        throw new AssertionError("Don't instantiate me");
    }

    public static Supplier<Boolean> isPresent(final SearchContext searchContext, final By locator)
    {
        return new Supplier<Boolean>()
        {
            public Boolean get()
            {
                return Check.elementExists(locator, searchContext);
            }
        };
    }

    public static Function<WebElement, Boolean> isPresent()
    {
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                // if we're here, the element was found
                return true;
            }
        };
    }

    public static Function<WebElement, Boolean> isVisible()
    {
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                return from.isDisplayed();
            }
        };
    }

    public static Function<WebElement, Boolean> isEnabled()
    {
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                return from.isEnabled();
            }
        };
    }

    public static Function<WebElement, Boolean> isSelected()
    {
       return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                return from.isSelected();
            }
        };
    }

    public static Function<WebElement, String> getTagName()
    {
        return new Function<WebElement, String>()
        {
            public String apply(WebElement from)
            {
                return from.getTagName();
            }
        };
    }

    public static Function<WebElement, String> getText()
    {
        return new Function<WebElement, String>()
        {
            public String apply(WebElement from)
            {
                return from.getText();
            }
        };
    }

    public static Function<WebElement, String> getValue()
    {
        return new Function<WebElement, String>()
        {
            public String apply(WebElement from)
            {
                return from.getAttribute("value");
            }
        };
    }

    public static Function<WebElement, String> getAttribute(final String attributeName)
    {
        checkNotNull(attributeName);
        return new Function<WebElement, String>()
        {
            public String apply(WebElement from)
            {
                return from.getAttribute(attributeName);
            }
        };
    }

    public static Function<WebElement, Boolean> hasAttribute(final String attributeName, final String expectedValue)
    {
        checkNotNull(attributeName);
        checkNotNull(expectedValue);
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                return expectedValue.equals(from.getAttribute(attributeName));
            }
        };
    }

    public static Function<WebElement, Boolean> hasClass(final String className)
    {
        checkNotNull(className);
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                return Check.hasClass(className, from);
            }
        };
    }

    public static Function<WebElement, Boolean> hasText(final String text)
    {
        checkNotNull(text);
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                // in case of text contains is the most common case
                return from.getText().contains(text);
            }
        };
    }

    public static Function<WebElement, Boolean> hasValue(final String value)
    {
        checkNotNull(value);
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(WebElement from)
            {
                return value.equals(from.getAttribute("value"));
            }
        };
    }
}
