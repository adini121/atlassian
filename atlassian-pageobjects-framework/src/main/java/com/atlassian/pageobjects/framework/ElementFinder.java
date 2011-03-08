package com.atlassian.pageobjects.framework;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.WebDriverElement;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Class that can be used in PageObjects to find Elements
 */
public class ElementFinder
{
    @Inject
    PageBinder pageBinder;

    /**
     * Creates a WebDriverElement using the specified locator
     * @param by Locator mechanism to use
     * @return Element that waits until its present in the DOM before executing actions.
     */

    public Element find(final By by)
    {
        return pageBinder.bind(WebDriverElement.class, by);
    }

    /**
     * Creates specified WebDriverElement using the specified locator
     * @param by Locator mechanism to use
     * @param elementClass The class of the element to create
     * @return An instance of specified WebDriverElement
     */
    public <T> T find(final By by, Class<T> elementClass)
    {
        return pageBinder.bind(elementClass, by);
    }
}
