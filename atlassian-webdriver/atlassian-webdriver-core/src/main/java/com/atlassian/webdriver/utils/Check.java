package com.atlassian.webdriver.utils;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;


/**
 * Utilities for doing simple checks on a page.
 */
public class Check
{

    private Check() {}

    /**
     * Checks that an element that matches the by param exists within another element.
     */
    public static boolean elementExists(By by, SearchContext el)
    {

        try
        {
            el.findElement(by);
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

        return true;
    }

    public static boolean elementIsVisible(By by, SearchContext context)
    {
        try
        {
            WebElement lookFor = context.findElement(by);
            return lookFor.isDisplayed();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public static boolean elementsAreVisible(By by, SearchContext context)
    {
        List<WebElement> elements = context.findElements(by);

        if (elements.size() > 0)
        {
            for (WebElement lookFor : elements)
            {
                if (!lookFor.isDisplayed())
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Checks to see if a specified element contains a specific class of not. The check is case-insensitive.
     *
     * @param className CSS class name to check for
     * @param element element to check
     * @return <code>true</code>, if <tt>element</tt> has CSS class with given <tt>className</tt>
     */
    public static boolean hasClass(String className, WebElement element)
    {
        final String classNameLowerCase = className.toLowerCase();
        String classValue = element.getAttribute("class");
        if (StringUtils.isEmpty(classValue))
        {
            return false;
        }
        classValue = classValue.toLowerCase();
        if (!classValue.contains(classNameLowerCase))
        {
            return false;
        }
        for (String singleClass : classValue.split("\\s+"))
        {
            if (classNameLowerCase.equals(singleClass))
            {
                return true;
            }
        }
        return false;
    }

}