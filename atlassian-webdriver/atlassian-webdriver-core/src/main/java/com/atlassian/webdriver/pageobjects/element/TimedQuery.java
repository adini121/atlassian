package com.atlassian.webdriver.pageobjects.element;

/**
 * Represents a repeatable query over the state of the DOM that waits until it returns the expected value.
 *
 * @param <T> type of the query result
 */
public interface TimedQuery<T>
{
    /**
     * Wait until query returns expected value or throw exception.
     * @param expected The expected value of the query
     */
    void waitFor(T expected);

    /**
     * Wait until query returns expected value and return boolean that represents result of the check
     * @param expected  The expected value of the query
     * @return Whether the actual value of the query matches the expected value.
     */
    boolean checkFor(T expected);
}
