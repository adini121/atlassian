package com.atlassian.webdriver.poller;

import com.atlassian.annotations.ExperimentalApi;

/**
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public interface StringValueQuery
{
    ExecutablePollerQuery contains(String value);
    ExecutablePollerQuery notContains(String value);

    /**
     * isEqual will evaluate to true if both values are null or equal.
     */
    ExecutablePollerQuery isEqual(String value);
    ExecutablePollerQuery notEqual(String value);

    /**
     * isEmpty should evaludate to true if the value is null
     * or is the empty String
     */
    ExecutablePollerQuery isEmpty();

    /**
     * isNotEmpty should evaluate to true if the value is not null
     * and is not the empty String.
     */
    ExecutablePollerQuery isNotEmpty();

    ExecutablePollerQuery endsWith(String value);
    ExecutablePollerQuery doesNotEndWith(String value);

    ExecutablePollerQuery matches(String value);
    ExecutablePollerQuery doesNotMatch(String value);

    ExecutablePollerQuery startsWith(String value);
    ExecutablePollerQuery doesNotStartWith(String value);

    ExecutablePollerQuery equalsIgnoresCase(String value);
}
