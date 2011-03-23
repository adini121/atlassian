package com.atlassian.pageobjects.elements.query.webdriver;

import com.atlassian.webdriver.utils.Check;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;

import javax.annotation.Nullable;

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
            public Boolean apply(@Nullable final WebElement from)
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
            public Boolean apply(@Nullable final WebElement from)
            {
                if (from instanceof RenderedWebElement)
                {
                    return ((RenderedWebElement)from).isDisplayed();
                }
                else return false;
            }
        };
    }

    public static Function<WebElement, Boolean> isEnabled()
    {
        return new Function<WebElement, Boolean>()
        {
            public Boolean apply(@Nullable final WebElement from)
            {
                return from.isEnabled();
            }
        };
    }

    public static Function<WebElement, Boolean> isSelected()
    {
       return new Function<WebElement, Boolean>()
        {
            public Boolean apply(@Nullable final WebElement from)
            {
                return from.isSelected();
            }
        };
    }


    public static Function<WebElement, String> getText()
    {
        return new Function<WebElement, String>()
        {
            public String apply(@Nullable final WebElement from)
            {
                return from.getText();
            }
        };
    }

    public static Function<WebElement, String> getValue()
    {
        return new Function<WebElement, String>()
        {
            public String apply(@Nullable final WebElement from)
            {
                return from.getValue();
            }
        };
    }

    public static Function<WebElement, String> getAttribute(final String attributeName)
    {
        checkNotNull(attributeName);
        return new Function<WebElement, String>()
        {
            public String apply(@Nullable final WebElement from)
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
            public Boolean apply(@Nullable final WebElement from)
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
            public Boolean apply(@Nullable final WebElement from)
            {
                final String classNameLowerCase = className.toLowerCase();
                String classValue = from.getAttribute(CLASS_ATTR_NAME);
                if (StringUtils.isEmpty(classValue))
                {
                    return false;
                }
                classValue = classValue.toLowerCase();
                if (!classValue.contains(classNameLowerCase))
                {
                    return false;
                }
                for (String singleClass : classValue.split("\\s"))
                {
                    if (classNameLowerCase.equals(singleClass))
                    {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
