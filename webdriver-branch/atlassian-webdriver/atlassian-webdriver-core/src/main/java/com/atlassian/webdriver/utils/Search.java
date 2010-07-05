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

        List<WebElement> elements = driver.findElements(searchElements);
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


}