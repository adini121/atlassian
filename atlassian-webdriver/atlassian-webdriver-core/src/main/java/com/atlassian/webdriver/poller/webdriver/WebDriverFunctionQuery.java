package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.ExecutablePollerQuery;
import com.atlassian.webdriver.poller.FunctionQuery;
import com.atlassian.webdriver.poller.Query;
import com.atlassian.webdriver.poller.webdriver.function.FalseFunction;

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

    enum FunctionQueryType {
        IS_TRUE,
        IS_FALSE;
    }

    private class WrappedWebDriverFunctionQuery implements Query
    {
        private final ConditionFunction func;
        private final FunctionQueryType type;

        public WrappedWebDriverFunctionQuery(ConditionFunction func, FunctionQueryType type)
        {
            this.func = func;
            this.type = type;
        }

        public ConditionFunction build()
        {
            switch(type)
            {
                case IS_TRUE:
                    return func;
                case IS_FALSE:
                    return new FalseFunction(func);
                default:
                    throw new UnsupportedOperationException("Unsupported function query type:" + type);
            }
        }
    }

    public WebDriverFunctionQuery(WebDriverQueryBuilder builder, ConditionFunction func)
    {
        this.builder = builder;
        this.func = func;
    }

    public ExecutablePollerQuery isTrue()
    {
        builder.add(new WrappedWebDriverFunctionQuery(func, FunctionQueryType.IS_TRUE));
        return new WebDriverPollerQuery.WebDriverExecutablePollerQuery(builder);
    }

    public ExecutablePollerQuery isFalse()
    {
        builder.add(new WrappedWebDriverFunctionQuery(func, FunctionQueryType.IS_FALSE));
        return new WebDriverPollerQuery.WebDriverExecutablePollerQuery(builder);
    }

}
