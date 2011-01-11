package com.atlassian.webdriver.pageobjects.element;

/**
 * Represents an HTML element that is expected in the DOM of a page, all queries return TimedQueries. 
 */
public interface TimedElement
{
    /**
     * Query representing the existence of this element on a page.
     *
     * @return TimedQuery that true if element is present on the page, false if element is not visible or timeout
     * expires.
     */
    TimedQuery<Boolean> isPresent();

    /**
     * Query representing visibility of this element on a page.
     *
     * @return TimedQuery that returns true if element is visible on the page, false if element is not visible 
     * or timeout expires.
     */
    TimedQuery<Boolean> isVisible();

    /**
     * Query representingt the element's given attribute.
     *
     * @param name Name of the attribute
     *
     * @return TimedQuery that returns the value of the given attribute, null if element does not have given attribute
     * or timeout expires.
     */
    TimedQuery<String> attribute(String name);

    /**
     * Query representing the element's inner text.
     *
     * @return TimedQuery that returns the inner text of the element, null if element does not have inner text
     * or timeout expires.
     */
    TimedQuery<String> text();

    /**
     * Query representing the element's 'value' attribute
     *
     * @return TimedQuery that returns the value of the 'value' attribute, null if element does not have a 'value'
     * attribute or timeout expires.
     */
    TimedQuery<String> value();
}
