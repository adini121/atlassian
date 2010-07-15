package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

/**
 * Search utilties
 */
public class Search
{

    private Search() {}

    /**
     * Searches for an element that contains a specific child element.
     * This is useful when multiple elements on a page are the same but a child node is unqiue.
     * @param searchElements the parent elements to look for the child element within.
     * @param childFind The child to look for.
     * @param driver
     * @return The parent web element that contains the child element.
     */
    public static WebElement findElementWithChildElement(By searchElements, By childFind, WebDriver driver)
    {
        return findElementWithChildElement(searchElements, childFind, driver.findElement(By.tagName("body")));
    }

    public static WebElement findElementWithChildElement(By searchElements, By childFind, WebElement context)
    {
        List<WebElement> elements = context.findElements(searchElements);
        Iterator<WebElement> iter = elements.iterator();

        while (iter.hasNext())
        {
            WebElement el = iter.next();
            if (Check.elementExists(childFind, el))
            {
                return el;
            }
        }

        return null;
    }

    public static WebElement findElementWithText(By searchElements, String textValue, WebDriver driver)
    {
        return findElementWithText(searchElements, textValue,  driver.findElement(By.tagName("body")));
    }

    public static WebElement findElementWithText(By searchElements, String textValue, WebElement context)
    {
        List<WebElement> elements = context.findElements(searchElements);
        Iterator<WebElement> iter = elements.iterator();

        while (iter.hasNext())
        {
            WebElement el = iter.next();
            if (el.getText().equals(textValue))
            {
                return el;
            }
        }

        return null;
    }


}