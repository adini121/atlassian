package com.atlassian.webdriver.pageobjects.element;

import org.openqa.selenium.By;

import java.util.List;

/**
 * Represents an HTML element that is expected on a DOM of a page
 */
public interface Element
{
    /**
     * Whether this element is currently on the DOM of the page
     *
     * @return True if this element tag exists in the DOM, false otherwise.
     */
    boolean isPresent();

    /**
     * Whether this element is visible on the page
     *
     * @return true if this element is visible on the page, false otherwise.
     */
    boolean isVisible();

    /**
     * Whether this element has the given class set
     * @param className The name of the class to check
     * @return true if this element's class attribute contains the given classname, false otherwise.
     */
    boolean hasClass(String className);

    /**
     * Get the value of a the given attribute of this element.
     *
     * @param name The name of the attribute.
     * @return The attribute's current value, or null if the value is not set
     */
    String attribute(String name);

    /**
     * Get the visible innerText of this element, including sub-elements, without any leading or trailing whitespaces.
     *
     * @return The innerText of this element.
     */
    String text();

    /**
     * Get the value of this element's "value" attribute.
     *
     * @return The value of this element's "value" attribute, or null if the value is not set.
     */
    String value();

    /**
     * Click this element
     */
    void click();

    /**
     * Simulate typing into an element
     * 
     * @param keysToSend 
     */
    void type(CharSequence... keysToSend);

    /**
     * Returns a list of element's that match the given locator within this element
     * @param locator The locator mecharnism
     * @return A list of elements that are located within this element.
     */
    List<Element> findAll(By locator);

    /**
     * Returns an element that will match the given locator within this element.
     *
     * @param locator The locator mechanism
     * @return An element that will be located within this element.
     */
    Element find(By locator);

    /**
     * Creates a timed element based on this element's locator.
     *
     * @return A TimedElement that is based on this element's locator.
     */
    TimedElement timed();

    /**
     * Gets a MouseEvents object for this element
     * @return MouseEvents object that can dispatch javascript events to this element.
     */
    WebDriverMouseEvents mouseEvents();
}
