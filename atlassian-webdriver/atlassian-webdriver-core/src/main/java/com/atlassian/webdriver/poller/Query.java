package com.atlassian.webdriver.poller;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;

/**
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public interface Query
{
    ConditionFunction build();
}
