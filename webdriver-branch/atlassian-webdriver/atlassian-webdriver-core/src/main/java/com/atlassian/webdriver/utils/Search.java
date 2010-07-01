package com.atlassian.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Search
{

    private Search() {}

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