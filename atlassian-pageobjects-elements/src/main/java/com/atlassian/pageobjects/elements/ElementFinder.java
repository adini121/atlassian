package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that can be used in PageObjects to find Elements
 */
public class ElementFinder
{
    @Inject
    AtlassianWebDriver driver;

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
     * Creates  a {@link PageElement} for each element that matches the given <tt>locator</tt>
     * using default timeout
     * @param by Locator mechanism to use
     * @return List of PageElements that match the given locator
     */
    public List<PageElement> findAll(final By by)
    {
        return findAll(by, TimeoutType.DEFAULT);
    }

    /**
     * Creates  a {@link PageElement} for each element that matches the given <tt>locator</tt>
     * using <tt>timeoutType</tt>
     * @param by Locator mechanism to use
     * @param timeoutType timeout for the element's timed operations
     * @return List of PageElements that match the given locator
     */
    public List<PageElement> findAll(final By by, TimeoutType timeoutType)
    {
        return findAll(by, PageElement.class, timeoutType);
    }

    /**
     * Creates {@link PageElement} extension of type <tt>T</tt> using the specified
     * <tt>locator</tt> and default timeout.
     *
     * @param by Locator mechanism to use
     * @param elementClass The class of the element to create
     * @return An instance that implements specified PageElement interface
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
     * @return An instance that implements specified PageElement interface
     */
    public <T extends PageElement> T find(final By by, Class<T> elementClass, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), by, timeoutType);
    }

    /**
     * Creates (@Link PageElement) extension of type <tt>T</tt> for each element that matches the given
     * <tt>locator</tt> with default timeout
     * @param by Locator mechanism to use
     * @param elementClass The class of the element to create
     * @return A list of objects that implement specified PageElement interface
     */
    public <T extends PageElement> List<T> findAll(final By by, Class<T> elementClass)
    {
        return findAll(by, elementClass, TimeoutType.DEFAULT);
    }

    /**
     * Creates (@Link PageElement) extension of type <tt>T</tt> for each element that matches the given
     * <tt>locator</tt> with <tt>timeoutType</tt>
     * @param by Locator mechanism to use
     * @param elementClass The class of the element to create
     * @param timeoutType timeout for the element's timed operations
     * @return A list of objects that implement specified PageElement interface
     */
    public <T extends PageElement> List<T> findAll(final By by, Class<T> elementClass, TimeoutType timeoutType)
    {
        List<T> elements = new LinkedList<T>();
        List<WebElement> webElements = driver.findElements(by);

        for(int i = 0; i < webElements.size(); i++)
        {
            elements.add(pageBinder.bind(WebDriverElementMappings.findMapping(elementClass),
                    WebDriverLocators.list(webElements.get(i), by, i, WebDriverLocators.root()), timeoutType));
        }

        return elements;
    }
}
