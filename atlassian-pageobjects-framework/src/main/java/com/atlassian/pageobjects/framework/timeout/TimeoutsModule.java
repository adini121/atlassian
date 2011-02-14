package com.atlassian.pageobjects.framework.timeout;

import com.google.inject.Binder;
import com.google.inject.Module;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A module implementation to provide {@link com.atlassian.pageobjects.framework.timeout.Timeouts} implementation
 * to the injector.
 *
 */
public class TimeoutsModule implements Module
{
    private final Timeouts timeouts;

    public TimeoutsModule(final Timeouts timeouts)
    {
        this.timeouts = checkNotNull(timeouts);
    }

    public void configure(final Binder binder)
    {
        binder.bind(Timeouts.class).toInstance(timeouts);
    }
}
