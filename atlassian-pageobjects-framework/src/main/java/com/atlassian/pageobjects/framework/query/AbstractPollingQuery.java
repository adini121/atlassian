package com.atlassian.pageobjects.framework.query;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract implementation of the {@link PollingQuery} interface.
 *
 * @since v4.3
 */
public class AbstractPollingQuery
{
    protected final long interval;
    protected final long defaultTimeout;

    protected AbstractPollingQuery(long interval, long defaultTimeout)
    {
        checkArgument(interval > 0, "interval should be > 0");
        checkArgument(defaultTimeout > 0, "defaultTimeout should be > 0");
        this.interval = interval;
        this.defaultTimeout = defaultTimeout;
    }

    protected AbstractPollingQuery(PollingQuery other)
    {
        this(checkNotNull(other, "other").interval(), other.defaultTimeout());
    }

    public long interval()
    {
        return interval;
    }

    public long defaultTimeout()
    {
        return defaultTimeout;
    }
}
