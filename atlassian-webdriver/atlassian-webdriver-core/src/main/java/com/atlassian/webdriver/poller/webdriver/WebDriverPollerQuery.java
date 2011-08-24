package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.element.WebElementRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.ElementQuery;
import com.atlassian.webdriver.poller.ExecutablePollerQuery;
import com.atlassian.webdriver.poller.FunctionQuery;
import com.atlassian.webdriver.poller.PollerQuery;
import com.atlassian.webdriver.poller.Query;
import com.google.common.base.Function;
import org.apache.commons.lang.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
class WebDriverPollerQuery implements PollerQuery
{
    private final WebDriverQueryBuilder queryBuilder;

    public WebDriverPollerQuery(WebDriverQueryBuilder builder)
    {
        this.queryBuilder = builder;
    }

    public FunctionQuery function(ConditionFunction func)
    {
        return new WebDriverFunctionQuery(queryBuilder, func);
    }

    public ElementQuery element(final By locator)
    {
        return element(locator, queryBuilder.getDriver());
    }

    public ElementQuery element(final By locator, final SearchContext context)
    {
        return new WebDriverElementQuery(queryBuilder, new WebElementRetriever(locator, context));
    }

    public ElementQuery element(final WebElement element)
    {
        return new WebDriverElementQuery(queryBuilder, new WebElementRetriever(element));
    }

    static class WebDriverExecutablePollerQuery
            implements ExecutablePollerQuery
    {

        private final WebDriverQueryBuilder queryBuilder;

        public WebDriverExecutablePollerQuery(WebDriverQueryBuilder queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public void execute()
        {
            Function<WebDriver,Boolean> func = queryBuilder.build();
            new AtlassianWebDriverWait(queryBuilder.getDriver(), queryBuilder.getTimeout()).until(func);
        }

        public PollerQuery and()
        {
            queryBuilder.add(new AndQuery());
            return new WebDriverPollerQuery(queryBuilder);
        }

        public PollerQuery or()
        {
            queryBuilder.add(new OrQuery());
            return new WebDriverPollerQuery(queryBuilder);
        }
    }

    static class AndQuery implements Query {
        public ConditionFunction build()
        {
            throw new NotImplementedException("This should not be called.");
        }
    }

    static class OrQuery implements Query {
        public ConditionFunction build()
        {
            throw new NotImplementedException("This should not be called.");
        }
    }
}
