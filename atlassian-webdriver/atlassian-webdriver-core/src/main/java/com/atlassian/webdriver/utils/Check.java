package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


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
            return ((RenderedWebElement) lookFor).isDisplayed();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public static boolean elementsAreVisible(By by, SearchContext context)
    {
        try
        {
            for (WebElement lookFor : context.findElements(by))
            {
                if (!((RenderedWebElement) lookFor).isDisplayed())
                {
                    return false;
                }
            }
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        return false;
    }

    /**
     * Checks to see if a specified element contains a specific class of not.
     */
    public static boolean hasClass(String className, WebElement el)
    {

        String classAttr = el.getAttribute("class");

        if (classAttr == null)
        {
            return false;
        }

        String[] classes = classAttr.split("\\s+");

        for (String clazz : classes)
        {
            if (clazz.equals(className))
            {
                return true;
            }

        }

        return false;
    }

}