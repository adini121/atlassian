package com.atlassian.webdriver.poller;

import com.atlassian.annotations.ExperimentalApi;

/**
 * WARNING: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public interface FunctionQuery
{
    ExecutablePollerQuery isTrue();
    ExecutablePollerQuery isFalse();
}
