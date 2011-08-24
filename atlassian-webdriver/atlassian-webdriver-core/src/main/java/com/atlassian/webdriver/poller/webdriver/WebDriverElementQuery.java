package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.element.WebElementRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.ElementQuery;
import com.atlassian.webdriver.poller.ExecutablePollerQuery;
import com.atlassian.webdriver.poller.Query;
import com.atlassian.webdriver.poller.StringValueQuery;
import com.atlassian.webdriver.poller.webdriver.function.FalseFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.ExistsFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.HasClassFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.IsEnabledFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.IsSelectedFunction;
import com.atlassian.webdriver.poller.webdriver.function.element.IsVisibleFunction;

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

    enum ElementQueryType {
        VISIBLE,
        IS_NOT_VISIBLE,
        EXISTS,
        DOES_NOT_EXIST,
        IS_SELECTED,
        IS_NOT_SELECTED,
        IS_ENABLED,
        IS_NOT_ENABLED,
        HAS_CLASS,
        DOES_NOT_HAVE_CLASS;
    }

    private class WrappedWebDriverElementQuery implements Query
    {
        private final WebElementRetriever webElementRetriever;
        private final ElementQueryType type;
        private final String value;

        public WrappedWebDriverElementQuery(WebElementRetriever webElementRetriever, ElementQueryType type)
        {
            this(webElementRetriever, type, null);
        }

        public WrappedWebDriverElementQuery(WebElementRetriever webElementRetriever, ElementQueryType type,
                String value)
        {
            this.webElementRetriever = webElementRetriever;
            this.type = type;
            this.value = value;
        }

        public ConditionFunction build()
        {
            switch(type)
            {
                case VISIBLE:
                    return new IsVisibleFunction(webElementRetriever);
                case IS_NOT_VISIBLE:
                    return new FalseFunction(new IsVisibleFunction(webElementRetriever));
                case EXISTS:
                    return new ExistsFunction(webElementRetriever);
                case DOES_NOT_EXIST:
                    return new FalseFunction(new ExistsFunction(webElementRetriever));
                case IS_SELECTED:
                    return new IsSelectedFunction(webElementRetriever);
                case IS_NOT_SELECTED:
                    return new FalseFunction(new IsSelectedFunction(webElementRetriever));
                case IS_ENABLED:
                    return new IsEnabledFunction(webElementRetriever);
                case IS_NOT_ENABLED:
                    return new FalseFunction(new IsEnabledFunction(webElementRetriever));
                case HAS_CLASS:
                    return new HasClassFunction(webElementRetriever, value);
                case DOES_NOT_HAVE_CLASS:
                    return new FalseFunction(new HasClassFunction(webElementRetriever, value));
                default:
                    throw new UnsupportedOperationException("Unsupported element query type:" + type);
            }
        }
    }

    public WebDriverElementQuery(WebDriverQueryBuilder builder, WebElementRetriever webElementRetriever)
    {
        this.builder = builder;
        this.webElementRetriever = webElementRetriever;
    }

    public ExecutablePollerQuery isVisible()
    {
        return createExecutablePollerQuery(ElementQueryType.VISIBLE);
    }

    public ExecutablePollerQuery isNotVisible()
    {
        return createExecutablePollerQuery(ElementQueryType.IS_NOT_VISIBLE);
    }

    public ExecutablePollerQuery exists()
    {
        return createExecutablePollerQuery(ElementQueryType.EXISTS);
    }

    public ExecutablePollerQuery doesNotExist()
    {
        return createExecutablePollerQuery(ElementQueryType.DOES_NOT_EXIST);
    }

    public StringValueQuery getAttribute(String attributeName)
    {
        return new WebDriverFieldQuery(builder, 
                new WebElementFieldRetriever(webElementRetriever,
                        WebElementFieldRetriever.FieldType.ATTRIBUTE, attributeName));
    }

    public ExecutablePollerQuery isSelected()
    {
        return createExecutablePollerQuery(ElementQueryType.IS_SELECTED);
    }

    public ExecutablePollerQuery isNotSelected()
    {
        return createExecutablePollerQuery(ElementQueryType.IS_NOT_SELECTED);
    }

    public ExecutablePollerQuery isEnabled()
    {
        return createExecutablePollerQuery(ElementQueryType.IS_ENABLED);
    }

    public ExecutablePollerQuery isNotEnabled()
    {
        return createExecutablePollerQuery(ElementQueryType.IS_NOT_ENABLED);
    }

    public ExecutablePollerQuery hasClass(String className)
    {
        return createExecutablePollerQuery(ElementQueryType.HAS_CLASS, className);
    }

    public ExecutablePollerQuery doesNotHaveClass(final String className)
    {
        return createExecutablePollerQuery(ElementQueryType.DOES_NOT_HAVE_CLASS, className);
    }

    public StringValueQuery getText()
    {
        return new WebDriverFieldQuery(builder,
                new WebElementFieldRetriever(webElementRetriever, WebElementFieldRetriever.FieldType.TEXT));
    }

    private ExecutablePollerQuery createExecutablePollerQuery(ElementQueryType type)
    {
        return createExecutablePollerQuery(type, null);
    }

    private ExecutablePollerQuery createExecutablePollerQuery(ElementQueryType type, String value)
    {
        builder.add(new WrappedWebDriverElementQuery(webElementRetriever, type, value));
        return new WebDriverPollerQuery.WebDriverExecutablePollerQuery(builder);
    }

}
