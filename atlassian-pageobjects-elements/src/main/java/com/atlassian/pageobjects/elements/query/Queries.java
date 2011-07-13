package com.atlassian.pageobjects.elements.query;

import static org.hamcrest.Matchers.equalTo;

/**
 * Common {@link com.atlassian.pageobjects.elements.query.TimedQuery}ies.
 *
 * @since v2.1
 */
public final class Queries
{
    private Queries()
    {
        throw new AssertionError("Don't instantiate me");
    }

    /**
     * Returns timed condition verifying that given query will evaluate to value equal to <tt>value</tt>. The timeouts
     * are inherited from the provided <tt>query</tt>
     *
     * @param value value that <tt>query</tt> should be equalt to
     * @param query the timed query
     * @param <T> type of the value
     * @return timed condition for query equality to value
     */
    public static <T> TimedCondition isEqual(T value, TimedQuery<T> query)
    {
        return Conditions.forMatcher(query, equalTo(value));
    }

}
