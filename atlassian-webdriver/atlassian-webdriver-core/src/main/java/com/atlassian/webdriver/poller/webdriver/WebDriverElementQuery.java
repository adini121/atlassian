package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;

import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.ElementQuery;
import com.atlassian.webdriver.poller.ExecutableWaiterQuery;
import com.atlassian.webdriver.poller.Query;
import com.atlassian.webdriver.poller.StringValueQuery;
import com.atlassian.webdriver.poller.webdriver.function.NotFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.ExistsFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.HasClassFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.IsEnabledFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.IsSelectedFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.IsVisibleFunction;
import com.atlassian.webdriver.poller.webdriver.retriever.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.retriever.WebElementRetriever;

/**
 *
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public class WebDriverElementQuery implements ElementQuery
{
    private final WebDriverQueryBuilder builder;
    private final WebElementRetriever webElementRetriever;

    public WebDriverElementQuery(WebDriverQueryBuilder builder, WebElementRetriever webElementRetriever)
    {
        this.builder = builder;
        this.webElementRetriever = webElementRetriever;
    }

    public ExecutableWaiterQuery isVisible()
    {
        return createExecutableWaiterQuery(new IsVisibleFunction(webElementRetriever));
    }

    public ExecutableWaiterQuery isNotVisible()
    {
        return createExecutableWaiterQuery(not(new IsVisibleFunction(webElementRetriever)));
    }

    public ExecutableWaiterQuery exists()
    {
        return createExecutableWaiterQuery(new ExistsFunction(webElementRetriever));
    }

    public ExecutableWaiterQuery doesNotExist()
    {
        return createExecutableWaiterQuery(not(new ExistsFunction(webElementRetriever)));
    }

    public StringValueQuery getAttribute(String attributeName)
    {
        return new WebDriverFieldQuery(builder, 
                WebElementFieldRetriever.newAttributeRetriever(webElementRetriever, attributeName));
    }

    public ExecutableWaiterQuery isSelected()
    {
        return createExecutableWaiterQuery(new IsSelectedFunction(webElementRetriever));
    }

    public ExecutableWaiterQuery isNotSelected()
    {
        return createExecutableWaiterQuery(not(new IsSelectedFunction(webElementRetriever)));
    }

    public ExecutableWaiterQuery isEnabled()
    {
        return createExecutableWaiterQuery(new IsEnabledFunction(webElementRetriever));
    }

    public ExecutableWaiterQuery isNotEnabled()
    {
        return createExecutableWaiterQuery(not(new IsEnabledFunction(webElementRetriever)));
    }

    public ExecutableWaiterQuery hasClass(String className)
    {
        return createExecutableWaiterQuery(new HasClassFunction(webElementRetriever, className));
    }

    public ExecutableWaiterQuery doesNotHaveClass(final String className)
    {
        return createExecutableWaiterQuery(not(new HasClassFunction(webElementRetriever, className)));
    }

    public StringValueQuery getText()
    {
        return new WebDriverFieldQuery(builder,
                WebElementFieldRetriever.newTextRetriever(webElementRetriever));
    }

    private ConditionFunction not(ConditionFunction func)
    {
        return new NotFunction(func);
    }

    private ExecutableWaiterQuery createExecutableWaiterQuery(final ConditionFunction function)
    {
        builder.add(new Query()
        {
            @Override
            public ConditionFunction build()
            {
                return function;
            }
        });
        return new WebDriverWaiterQuery.WebDriverExecutableWaiterQuery(builder);
    }

}
