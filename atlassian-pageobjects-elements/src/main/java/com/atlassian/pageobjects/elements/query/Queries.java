package com.atlassian.pageobjects.elements.query;

import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import com.google.common.base.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utilities to create and manipulate timed queries
 *
 * @since 2.1
 */
public final class Queries
{

    private Queries()
    {
        throw new AssertionError("Don't instantiate me");
    }

    /**
     * Returns a timed query, with current evaluation is based on a value provided by given <tt>supplier</tt>. The supplier
     * will be periodically called to compute the value of the query.
     *
     * @param timeouts an instance of timeouts to use for configured the new condition
     * @param supplier supplier of the query evaluation
     * @return new query based on supplier
     */
    public static <T> TimedQuery<T> forSupplier(Timeouts timeouts, final Supplier<T> supplier)
    {
        checkNotNull("timeouts", timeouts);
        checkNotNull("supplier", supplier);
        return new AbstractTimedQuery<T>(timeouts.timeoutFor(TimeoutType.DEFAULT),
                timeouts.timeoutFor(TimeoutType.EVALUATION_INTERVAL), ExpirationHandler.RETURN_CURRENT) {
            @Override
            protected T currentValue() {
                return supplier.get();
            }

            @Override
            protected boolean shouldReturn(T currentEval)
            {
                return true;
            }
        };
    }
}
