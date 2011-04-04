package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;

/**
 * Represents an HTML element that is expected in the DOM of a page, all queries return TimedQueries.
 * 
 */
public interface TimedElement
{
    /**
     * Query representing the existence of this element on a page.
     *
     * @return TimedQuery that true if element is present on the page, false if element is not visible or timeout
     * expires.
     */
    TimedCondition isPresent();

    /**
     * Query representing visibility of this element on a page.
     *
     * @return TimedQuery that returns true if element is visible on the page, false if element is not visible 
     * or timeout expires.
     */
    TimedCondition isVisible();

    /**
     * Query representing whether this element is enabled on a page.
     *
     * @return TimedQuery that returns true if element is enabled on the page, false if element is disabled or
     * timeout expires.
     */
    TimedCondition isEnabled();

    /**
     * Query representing whether this element is selected on a page.
     *
     * @return TimedQuery that returns true if element is selected on the page, false if element is not selected or
     * timeout expires.
     */
    TimedCondition isSelected();
 
    /**
     * Query representing whether this element has the given classname set.
     * @param className The name of the class to check
     * @return TimedQuery that returns true if element has given classname set, false if element does not have the
     * given classname set or timeout expires.
     */
    TimedCondition hasClass(String className);

    /**
     * Query representing the element's given attribute.
     *
     * @param name Name of the attribute
     *
     * @return TimedQuery that returns the value of the given attribute, null if element does not have given attribute
     * or timeout expires.
     */
    TimedQuery<String> getAttribute(String name);

    /**
     * Query representing whether this element has the given attribute set
     * @param name Name of the attribute
     * @param value expected attribute value
     * @return TimedQuery that returns true if element has given attribute set, false if element does not  have the
     * given attribute set or timeout expires
     */
    TimedCondition hasAttribute(String name, String value);

    /**
     * Query representing the element's inner text.
     *
     * @return TimedQuery that returns the inner text of the element, null if element does not have inner text
     * or timeout expires.
     */
    TimedQuery<String> getText();

     /**
     * Query representing whether this element's innerText is equals to the provided string
     * @param text The expected innerText string
     * @return TimedQuery that returns true if this element has given innerText set, false if element does not have the
      * given attribute set or timeout expires
     */
    TimedCondition hasText(String text);

    /**
     * Query representing the element's tag name
     *
     * @return TimedQuery that returns the tagname of the element
     */
    TimedQuery<String> getTagName();

    /**
     * Query representing the element's 'value' attribute
     *
     * @return TimedQuery that returns the value of the 'value' attribute, null if element does not have a 'value'
     * attribute or timeout expires.
     */
    TimedQuery<String> getValue();
}