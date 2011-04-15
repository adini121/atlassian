package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
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
     * Creates  {@link PageElement} implementation
     * using the specified <tt>locator</tt> and default timeout.
     *
     * @param by Locator mechanism to use
     * @return Element that waits until its present in the DOM before executing actions.
     */

    public PageElement find(final By by)
    {
        return pageBinder.bind(WebDriverElement.class, by);
    }

    /**
     * Creates {@link PageElement} implementation
     * using the specified <tt>locator</tt> and given <tt>timeoutType</tt>.
     *
     * @param by Locator mechanism to use
     * @param timeoutType timeout for the element's timed operations
     * @return Element that waits until its present in the DOM before executing actions.
     */

    public PageElement find(final By by, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElement.class, by, timeoutType);
    }

    /**
     * Creates {@link PageElement} extension of type <tt>T</tt> using the specified
     * <tt>locator</tt> and default timeout.
     *
     * @param by Locator mechanism to use
     * @param elementClass The class of the element to create
     * @return An instance of specified WebDriverElement
     */
    public <T extends PageElement> T find(final By by, Class<T> elementClass)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), by);
    }

    /**
     * Creates {@link PageElement} extension of type <tt>T</tt> using the specified
     * <tt>locator</tt> and given <tt>timeoutType</tt>
     *
     * @param by Locator mechanism to use
     * @param elementClass The class of the element to create
     * @param timeoutType timeout for the element's timed operations
     * @return An instance of specified WebDriverElement
     */
    public <T extends PageElement> T find(final By by, Class<T> elementClass, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), by, timeoutType);
    }
}
