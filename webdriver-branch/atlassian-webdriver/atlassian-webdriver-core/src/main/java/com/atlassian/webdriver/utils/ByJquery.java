package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Provides an extension to By so that jQuery selectors can be used.
 * By calling the ByJquery.$ method will ensure that jQuery get's loaded into the page.
 * It is namespaced away within the javascript so that it doens't override another version of jQuery on the page.
 * This allows the ByJquery locator to be dependent on it's own version of jQuery.
 *
 * same usages of ByJquery include:
 * <code>
 * ByJquery.$("div.className li");
 * ByJQuery.$("('div.className li a').parent('div')");
 *</code>
 * It accepts simple searches like the first example that don't need to be wrapped in brackets, but
 * if you want to call another jQuery method like <em>.parent</em> the first selector needs to be wrapped in brakcets.
 */
public abstract class ByJquery extends By
{
    private static WebDriver driver;

    public abstract List<WebElement> findElements(SearchContext context);

    public static void init(WebDriver driver)
    {
        ByJquery.driver = driver;
    }

    public static ByJquery $(final String selector)
    {
        if (selector == null)
        {
            throw new IllegalArgumentException("The jquery selector cannot be null.");
        }

        final String realSelector = fixSelector(selector);

        loadJqueryLocator(driver);

        return new ByJquery()
        {
            @Override
            public List<WebElement> findElements(final SearchContext context)
            {

                if (context instanceof WebElement)
                {
                    Object[] args = {"WD.byJquery.$(context).find" + realSelector,(WebElement)context};
                    return JavaScriptUtils.execute("return WD.byJquery.execute(arguments[0],arguments[1])", driver, args);
                }
                else
                {
                    Object[] args = {"WD.byJquery.$" + realSelector};
                    return  JavaScriptUtils.execute("return WD.byJquery.execute(arguments[0])", driver, args);
                }
            }

            @Override
            public WebElement findElement(final SearchContext context)
            {

                if (context instanceof WebElement)
                {
                    Object[] args = {"WD.byJquery.$(context).find" + realSelector,(WebElement)context};
                    return JavaScriptUtils.execute("return WD.byJquery.executeOne(arguments[0],arguments[1])", driver, args);
                }
                else
                {
                    Object[] args = {"WD.byJquery.$" + realSelector};
                    return JavaScriptUtils.execute("return WD.byJquery.executeOne(arguments[0])", driver, args);
                }
            }

            @Override
            public String toString()
            {
                return "jQuery selector: " + selector;
            }
        };

    }

    private static String fixSelector(String selector)
    {
        return !selector.startsWith("('") ? "('" + selector + "')" : selector ;
    }

    /**
     * Find a single element. Override this method if necessary.
     * @param context A context to use to find the element
     * @return The WebElement that matches the selector
     */
    public WebElement findElement(SearchContext context) {
        List<WebElement> allElements = findElements(context);
        if (allElements == null || allElements.size() == 0)
            throw new NoSuchElementException("Cannot locate an element using " + toString());
        return allElements.get(0);
    }

    private static void loadJqueryLocator(WebDriver driver)
    {
        if (!isLoaded(driver))
        {
            JavaScriptUtils.loadScript("jqueryLocator.js", driver);
            JavaScriptUtils.loadScript("jquery-1.4.2.min.js", driver);
            JavaScriptUtils.execute("WD.loadJquery()", driver);
        }

    }

    private static boolean isLoaded(WebDriver driver)
    {
        String js = "return window.WD != undefined && window.WD.byJquery != undefined";
        Boolean jQueryLocatorDefined = JavaScriptUtils.execute(js, driver);
        return jQueryLocatorDefined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        By by = (By) o;

        return toString().equals(by.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
