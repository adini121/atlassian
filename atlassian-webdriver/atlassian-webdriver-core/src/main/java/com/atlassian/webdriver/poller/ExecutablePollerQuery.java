package com.atlassian.webdriver.poller;

import com.atlassian.annotations.ExperimentalApi;

/**
 * WARNING: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public interface ExecutablePollerQuery
{
    /**
     * Executes the built up poller query.
     */
    void execute();

    /**
     * Allows chaining of queries together and will pass when
     * both queries evaluates to true
     */
    PollerQuery and();

    /**
     * Allows chaining of queries together and will pass when
     * either query evaluates to true
     */
    PollerQuery or();
}
