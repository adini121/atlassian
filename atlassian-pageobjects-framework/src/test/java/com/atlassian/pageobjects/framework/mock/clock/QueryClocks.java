package com.atlassian.pageobjects.framework.mock.clock;

import com.atlassian.pageobjects.framework.query.util.Clock;

/**
 * {@link Clock} implementations for testing timed queries and conditions.
 *
 * @since v4.3
 */
public final class QueryClocks
{
    private QueryClocks()
    {
        throw new AssertionError("Don't instantiate me");
    }

    public static Clock forInterval(long interval)
    {
        return new CompositeClock(new ConstantClock(0L)).addClock(2, new PeriodicClock(0L, interval, 2));
    }
}