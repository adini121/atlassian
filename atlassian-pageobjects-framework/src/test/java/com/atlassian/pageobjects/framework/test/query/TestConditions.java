package com.atlassian.pageobjects.framework.test.query;

import com.atlassian.pageobjects.framework.query.Conditions;
import com.atlassian.pageobjects.framework.query.TimedCondition;
import com.atlassian.pageobjects.framework.query.TimedQuery;
import org.junit.Test;

import static com.atlassian.pageobjects.framework.mock.MockCondition.FALSE;
import static com.atlassian.pageobjects.framework.mock.MockCondition.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test case for {@link com.atlassian.pageobjects.framework.query.Conditions}.
 *
 * @since v4.2
 */
public class TestConditions
{
    @Test
    public void testNot()
    {
        TimedQuery<Boolean> notFalse = Conditions.not(FALSE);
        assertTrue(notFalse.now());
        TimedQuery<Boolean> notTrue = Conditions.not(TRUE);
        assertFalse(notTrue.now());
    }

    @Test
    public void andTrueOnlyShouldResultInTrue()
    {
        TimedCondition and = Conditions.and(TRUE, TRUE, TRUE);
        assertTrue(and.now());
    }

    @Test
    public void trueAndFalseShouldResultInFalse()
    {
        TimedCondition and = Conditions.and(TRUE, FALSE, TRUE, TRUE);
        assertFalse(and.now());
    }

    @Test
    public void trueOrFalseShouldResultInTrue()
    {
        TimedCondition or = Conditions.or(FALSE, FALSE, TRUE, FALSE);
        assertTrue(or.now());
    }

    @Test
    public void orFalseOnlyShouldResultInFalse()
    {
        TimedCondition or = Conditions.or(FALSE, FALSE, FALSE, FALSE);
        assertFalse(or.now());
    }
}