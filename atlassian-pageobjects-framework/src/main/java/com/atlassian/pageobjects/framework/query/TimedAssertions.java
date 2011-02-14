package com.atlassian.pageobjects.framework.query;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Common assertions for timeout-based queries inheriting from
 * {@link PollingQuery}.
 *
 * @see PollingQuery
 * @see TimedQuery
 * @see TimedCondition
 * @since v4.3
 */
public final class TimedAssertions
{
    private TimedAssertions() {
        throw new AssertionError("Don't instantiate me");
    }


    /**
     * Assert that <tt>condition</tt> is <code>true</code> by default timeout
     *
     * @param condition condition to check
     */
    public static void assertTrueByDefaultTimeout(TimedQuery<Boolean> condition)
    {
        assertThat(condition, is(true), byDefaultTimeout());
    }

    /**
     * Assert that <tt>condition</tt> is <code>true</code> by default timeout, with custom error message.
     *
     * @param message error message
     * @param condition condition to check
     */
    public static void assertTrueByDefaultTimeout(String message, TimedQuery<Boolean> condition)
    {
        assertThat(condition, is(true), byDefaultTimeout());
    }

    /**
     * Assert that <tt>condition</tt> is <code>false</code> by default timeout
     *
     * @param condition condition to check
     */
    public static void assertFalseByDefaultTimeout(TimedQuery<Boolean> condition)
    {
        assertThat(condition, is(false), byDefaultTimeout());
    }

    /**
     * Assert that <tt>condition</tt> is <code>false</code> by default timeout, with custom error message.
     *
     * @param message error message
     * @param condition condition to check
     */
    public static void assertFalseByDefaultTimeout(String message, TimedQuery<Boolean> condition)
    {
        assertThat(condition, is(false), byDefaultTimeout());
    }

    /**
     * Assert that <tt>query</tt> evaluates to actual value that is equal to <tt>expectedValue</tt> by default timeout.
     *
     * @param expectedValue expected value
     * @param query query to check
     */
    public static <T> void assertEqualsByDefaultTimeout(T expectedValue, TimedQuery<T> query)
    {
        assertThat(query, equalTo(expectedValue), byDefaultTimeout());
    }

    /**
     * Assert that <tt>query</tt> evaluates to actual value that is equal to <tt>expectedValue</tt> by default timeout,
     * with custom error message.
     *
     * @param message error message
     * @param expectedValue expected value
     * @param query query to check
     */
    public static <T> void assertEqualsByDefaultTimeout(String message, T expectedValue, TimedQuery<T> query)
    {
        assertThat(query, equalTo(expectedValue), byDefaultTimeout());
    }


    /**
     * <p>
     * Assert that result of given <tt>query</tt> fulfils certain condition specified by given the <tt>matcher</tt>, by
     * given <tt>timeout</tt>.
     *
     * <p>
     * Use any matcher available from the libraries (e.g. Hamcrest, JUnit etc.), or a custom one.
     *
     * @param query timed query to verify
     * @param matcher a matcher representing the assertion condition
     * @param timeout timeout of the assertion
     * @see Matcher
     * @see #now()
     * @see #by(long)
     * @see #by(long, java.util.concurrent.TimeUnit)
     * @see #byDefaultTimeout()
     */
    public static <T> void assertThat(TimedQuery<T> query, Matcher<T> matcher, AssertionTimeout timeout)
    {
        assertThat(null, query, matcher, timeout);
    }


    /**
     * <p>
     * Assert that result of given <tt>query</tt> fulfils certain condition specified by given the <tt>matcher</tt>, by
     * given <tt>timeout</tt>.
     *
     * <p>
     * For matching condition, use any matcher available from the libraries (e.g. Hamcrest, JUnit etc.), or a custom one.
     *
     * <p>
     * To specify desired timeout, use one of four provided timeouts: {@link #now()}, {@link #byDefaultTimeout()},
     * #{@link #by(long)}, {@link #by(long, java.util.concurrent.TimeUnit)}.
     *
     * @param message message displayed for failed assertion
     * @param query timed query to verify
     * @param matcher a matcher representing the assertion condition
     * @param timeout timeout of the assertion
     * @see Matcher
     * @see #now()
     * @see #by(long)
     * @see #by(long, java.util.concurrent.TimeUnit)
     * @see #byDefaultTimeout()
     */
    public static <T> void assertThat(String message, TimedQuery<T> query, Matcher<T> matcher, AssertionTimeout timeout)
    {
        checkNotNull(timeout);
        final AssertingCondition<T> assertion = new AssertingCondition<T>(query, matcher);
        if (!timeout.evaluate(assertion))
        {
            throw new AssertionError(buildMessage(message, assertion, matcher, timeout));
        }
    }

    private static <T> String buildMessage(String message, AssertingCondition<T> assertion, Matcher<T> matcher, AssertionTimeout timeout)
    {
        final Description answer = new StringDescription();
        if (StringUtils.isNotEmpty(message))
        {
            answer.appendText(message).appendText(":\n");
        }
        return answer.appendText("Query ").appendValue(assertion.query).appendText("\nExpected: ")
                .appendDescriptionOf(matcher).appendText(timeout.msgTimeoutSuffix(assertion))
                .appendText("\nGot (last value): ").appendValue(assertion.lastValue).toString();
    }

    private static final class AssertingCondition<T> extends AbstractTimedCondition
    {
        private final TimedQuery<T> query;
        private final Matcher<T> matcher;

        private T lastValue;

        public AssertingCondition(final TimedQuery<T> query, final Matcher<T> matcher)
        {
            super(query);
            this.query = checkNotNull(query);
            this.matcher = checkNotNull(matcher);
        }

        @Override
        protected Boolean currentValue()
        {
            lastValue = query.now();
            return matcher.matches(lastValue);
        }
    }


    public static abstract class AssertionTimeout
    {
        // we don't want this to be extended by anybody
        private AssertionTimeout() {}

        abstract boolean evaluate(TimedCondition condition);

        abstract String msgTimeoutSuffix(TimedCondition condition);
    }


    /**
     * Timeout indicating that the assertion method will evaluate the assertion condition immediately, without waiting.
     *
     * @return new immediate assertion timeout
     */
    public static AssertionTimeout now()
    {
        return new AssertionTimeout()
        {
            @Override
            boolean evaluate(final TimedCondition condition)
            {
                return condition.now();
            }
            @Override
            String msgTimeoutSuffix(final TimedCondition condition)
            {
                return " immediately";
            }
        };
    }

    /**
     * Timeout indicating that the assertion method will wait for the default timeout of exercised timed query for
     * the condition to become <code>true</code>.
     *
     * @return new default assertion timeout
     */
    public static AssertionTimeout byDefaultTimeout()
    {
        return new AssertionTimeout()
        {
            @Override
            boolean evaluate(final TimedCondition condition)
            {
                return condition.byDefaultTimeout();
            }
            @Override
            String msgTimeoutSuffix(final TimedCondition condition)
            {
                return " by " + condition.defaultTimeout() + "ms (default timeout)";
            }
        };
    }

    /**
     * Custom assertion timeout expressed in milliseconds. E.g. <code>TimedAssertions.by(500L);</code>
     * passed to an assertion method will make it wait 500 milliseconds for given condition to become <code>true</code>.
     *
     * @param timeoutInMillis number of milliseconds to wait for the assertion condition
     * @return new custom timeout assertion
     */
    public static AssertionTimeout by(final long timeoutInMillis)
    {
        return new AssertionTimeout()
        {
            @Override
            boolean evaluate(final TimedCondition condition)
            {
                return condition.by(timeoutInMillis);
            }
            @Override
            String msgTimeoutSuffix(final TimedCondition condition)
            {
                return " by " + timeoutInMillis + "ms";
            }
        };
    }

    /**
     * Custom assertion timeout expressed in a number of time units. E.g. <code>TimedAssertions.by(5, TimeUnit.SECONDS);</code>
     * passed to an assertion method will make it wait 5 seconds for given condition to become <code>true</code>.
     *
     * @param timeout timeout count
     * @param unit unit of the timeout
     * @return new custom timeout assertion
     */
    public static AssertionTimeout by(final long timeout, final TimeUnit unit)
    {
        return new AssertionTimeout()
        {
            @Override
            boolean evaluate(final TimedCondition condition)
            {
                return condition.by(timeout, unit);
            }
            @Override
            String msgTimeoutSuffix(final TimedCondition condition)
            {
                return " by " + unit.toMillis(timeout) + "ms";
            }
        };
    }

}
