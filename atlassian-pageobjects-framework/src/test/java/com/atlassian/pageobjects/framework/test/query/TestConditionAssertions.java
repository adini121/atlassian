package com.atlassian.pageobjects.framework.test.query;

import com.atlassian.pageobjects.framework.mock.MockCondition;
import com.atlassian.pageobjects.framework.mock.clock.QueryClocks;
import com.atlassian.pageobjects.framework.query.TimedCondition;
import org.junit.Test;

import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertThat;
import static com.atlassian.pageobjects.framework.query.TimedAssertions.by;
import static com.atlassian.pageobjects.framework.query.TimedAssertions.byDefaultTimeout;
import static com.atlassian.pageobjects.framework.query.TimedAssertions.now;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;

/**
 * Test case for {@link com.atlassian.pageobjects.framework.query.TimedAssertions} using mock timed conditions.
 *
 */
public class TestConditionAssertions
{
    @Test
    public void shouldPassForPassingCondition()
    {
        assertThat(passingCondition(), is(true), byDefaultTimeout());
        assertThat(passingCondition(), is(true), by(1000));
        assertThat(passingCondition(), is(true), now());
    }

    @Test
    public void shouldCallTestedConditionUntilItReturnsTrue()
    {
        MockCondition tested = new MockCondition(false, false, false, true)
                .withClock(QueryClocks.forInterval(MockCondition.DEFAULT_INTERVAL));
        assertThat(tested, is(true), by(1000));
        assertEquals(4, tested.callCount());
    }

    @Test
    public void shouldCallTestedConditionUntilItReturnsFalse()
    {
        MockCondition tested = new MockCondition(true, true, true, false)
                .withClock(QueryClocks.forInterval(MockCondition.DEFAULT_INTERVAL));
        assertThat(tested, is(false), by(1000));
        assertEquals(4, tested.callCount());
    }


    @Test
    public void shouldProduceMeaningfulErrorMessage()
    {
        try
        {
            assertThat(failingCondition(), is(true), byDefaultTimeout());
            throw new IllegalStateException("Should fail");
        }
        catch(AssertionError e)
        {
            assertEquals("Query <Failing Condition>\n"
                    + "Expected: is <true> by 500ms (default timeout)\n"
                    + "Got (last value): <false>", e.getMessage());
        }
    }

    @Test
    public void shouldProduceMeaningfulErrorMessageForNegatedAssertion()
    {
        try
        {
            assertThat(passingCondition(), is(false), byDefaultTimeout());
            throw new IllegalStateException("Should fail");
        }
        catch(AssertionError e)
        {
            assertEquals("Query <Passing Condition>\n"
                    + "Expected: is <false> by 500ms (default timeout)\n"
                    + "Got (last value): <true>", e.getMessage());
        }
    }


    private MockCondition passingCondition()
    {
        return new MockCondition(true)
        {
            public String toString()
            {
                return "Passing Condition";
            }
        };
    }

    private TimedCondition failingCondition()
    {
        return new MockCondition(false)
        {
            public String toString()
            {
                return "Failing Condition";
            }
        };
    }

}
