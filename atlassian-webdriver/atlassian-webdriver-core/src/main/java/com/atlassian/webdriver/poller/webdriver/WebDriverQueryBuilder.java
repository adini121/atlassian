package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.Query;
import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2.1
 */
class WebDriverQueryBuilder
{
    private final AtlassianWebDriver driver;
    private final int timeout;
    List<Query> queries = new ArrayList<Query>();

    public WebDriverQueryBuilder(AtlassianWebDriver driver, int timeout)
    {
        this.driver = driver;
        this.timeout = timeout;
    }

    public WebDriverQueryBuilder add(Query query) {
        this.queries.add(query);
        return this;
    }

    public Function<WebDriver, Boolean> build() {

        ConditionFunction builtFunction = null;
        for(int i = 0; i < queries.size(); i++)
        {
            Query query = queries.get(i);

            ConditionFunction func;
            if (isAndQuery(query))
            {
                i++; // We want to skip the next function as we already consumed it.
                ConditionFunction nextFunc = queries.get(i).build();
                func = new AndFunction(builtFunction, nextFunc);
            }
            else if (isOrQuery(query))
            {
                i++; // We want to skip the next function as we already consumed it.
                ConditionFunction nextFunc = queries.get(i).build();
                func = new OrFunction(builtFunction, nextFunc);
            }
            else
            {
                 func = query.build();
            }

            builtFunction = func;
        }

        return builtFunction;
    }

    public AtlassianWebDriver getDriver()
    {
        return driver;
    }

    public int getTimeout()
    {
        return timeout;
    }

    private boolean isAndQuery(Query query)
    {
        return query.getClass().isAssignableFrom(WebDriverPollerQuery.AndQuery.class);
    }

    private boolean isOrQuery(Query query)
    {
        return query.getClass().isAssignableFrom(WebDriverPollerQuery.OrQuery.class);
    }

    private static class AndFunction implements ConditionFunction
    {
        private final Function<WebDriver, Boolean> func1;
        private final Function<WebDriver, Boolean> func2;

        public AndFunction(Function<WebDriver, Boolean> func1, Function<WebDriver, Boolean> func2)
        {
            this.func1 = func1;
            this.func2 = func2;
        }

        public Boolean apply(WebDriver driver)
        {
            return func1.apply(driver) && func2.apply(driver);
        }
    }

    private static class OrFunction implements ConditionFunction
    {
        private final Function<WebDriver, Boolean> func1;
        private final Function<WebDriver, Boolean> func2;

        public OrFunction(Function<WebDriver, Boolean> func1, Function<WebDriver, Boolean> func2)
        {
            this.func1 = func1;
            this.func2 = func2;
        }

        public Boolean apply(WebDriver driver)
        {
            return func1.apply(driver) || func2.apply(driver);
        }
    }
}
