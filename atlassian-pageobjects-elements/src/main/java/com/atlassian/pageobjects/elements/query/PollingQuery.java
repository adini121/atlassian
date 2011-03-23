package com.atlassian.pageobjects.elements.query;

/**
 * A query over the state of the current test that is capable of being repeated at given intervals in an attempt to
 * wait for a desired result.
 *
 */
public interface PollingQuery
{
    public static final long DEFAULT_INTERVAL = 100L;

    /**
     * An interval (in milliseconds) that will be used to periodically evaluate the query.
     *
     * @return evaluation interval of this query.
     */
    long interval();

    /**
     * Default timeout (in milliseconds) of this query in the current test context.
     *
     * @return default timeout of this query
     */
    long defaultTimeout();
}
