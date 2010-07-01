package com.atlassian.webdriver.utils;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Check
{

    private Check() {}

    public static boolean elementExists(By by)
    {
        return elementExists(by, null);
    }

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

    public static boolean pageContains(String text)
    {
        return AtlassianWebDriver.getDriver().getPageSource().contains(text);
    }


}