package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.ExecutableWaiterQuery;
import com.atlassian.webdriver.poller.FunctionQuery;
import com.atlassian.webdriver.poller.Query;
import com.atlassian.webdriver.poller.webdriver.function.NotFunction;

/**
 *
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
class WebDriverFunctionQuery implements FunctionQuery
{
    private final WebDriverQueryBuilder builder;
    private final ConditionFunction func;

    public WebDriverFunctionQuery(WebDriverQueryBuilder builder, ConditionFunction func)
    {
        this.builder = builder;
        this.func = func;
    }

    public ExecutableWaiterQuery isTrue()
    {
        builder.add(new Query()
        {
            @Override
            public ConditionFunction build()
            {
                return func;
            }
        });
        return new WebDriverWaiterQuery.WebDriverExecutableWaiterQuery(builder);
    }

    public ExecutableWaiterQuery isFalse()
    {
        builder.add(new Query()
        {
            @Override
            public ConditionFunction build()
            {
                return new NotFunction(func);
            }
        });
        return new WebDriverWaiterQuery.WebDriverExecutableWaiterQuery(builder);
    }

}
