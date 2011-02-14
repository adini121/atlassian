package com.atlassian.pageobjects.framework.query;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * <p>
 * A marker interface for a special type of {@link com.atlassian.pageobjects.framework.query.TimedQuery}. This query
 * has precise semantics of its timed operations, namely:
 * <ul>
 * <li>it waits for the underlying condition to be <code>true</code> within given timeout and returns <code>true</code> if
 * successful
 * <li>otherwise, if the timeout expires, it returns <code>false<code>
 * </ul>
 *
 */
@NotThreadSafe
public interface TimedCondition extends TimedQuery<Boolean>
{
}
