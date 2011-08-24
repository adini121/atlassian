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

    ExecutablePollerQuery isEqual(String value);
    ExecutablePollerQuery notEqual(String value);

    ExecutablePollerQuery isEmpty();
    ExecutablePollerQuery isNotEmpty();

    ExecutablePollerQuery endsWith(String value);
    ExecutablePollerQuery doesNotEndWith(String value);

    ExecutablePollerQuery matches(String value);
    ExecutablePollerQuery doesNotMatch(String value);

    ExecutablePollerQuery startsWith(String value);
    ExecutablePollerQuery doesNotStartWith(String value);

    ExecutablePollerQuery equalsIgnoresCase(String value);
}
