package com.atlassian.webdriver.utils;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


/**
 * Utilities for doing simple checks on a page.
 */
public class Check
{

    private Check() {}

    /**
     * Checks that an element that matches the by exists.
     * @param by
     * @return
     */
    public static boolean elementExists(By by)
    {
        return elementExists(by, null);
    }

    /**
     * Checks that an element that matches the by param exists within another element.
     * @param by
     * @param el
     * @return
     */
    public static boolean elementExists(By by, WebElement el)
    {

        try
        {
            if (el == null)
            {
                AtlassianWebDriver.getDriver().findElement(by);
            }
            else
            {
                el.findElement(by);
            }
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

        return true;

    }

    /**
     * Checks to see if a specified element contains a specific class of not.
     * @param className
     * @param el
     * @return
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

    /**
     * Checks to see if the given text is found on the page or not.
     * This is done by checking the page source.
     * @param text
     * @return
     */
    public static boolean pageContains(String text)
    {
        return AtlassianWebDriver.getDriver().getPageSource().contains(text);
    }


}