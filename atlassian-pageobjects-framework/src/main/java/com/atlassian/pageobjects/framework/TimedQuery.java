package com.atlassian.pageobjects.framework;

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
}
