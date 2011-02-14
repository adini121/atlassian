package com.atlassian.pageobjects.framework.query.util;

/**
 * Provides information about current time.
 *
 */
public interface Clock
{

    /**
     * Current time according to this clock.
     *
     * @return current time
     */
    long currentTime();
}
