package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.ExecutablePollerQuery;
import com.atlassian.webdriver.poller.Query;
import com.atlassian.webdriver.poller.StringValueQuery;
import com.atlassian.webdriver.poller.webdriver.function.FalseFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.ContainsFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.EndsWithFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.EqualsIgnoresCaseFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.IsEmptyFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.IsEqualFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.MatchesFunction;
import com.atlassian.webdriver.poller.webdriver.function.field.StartsWithFunction;

/**
 *
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public class WebDriverFieldQuery implements StringValueQuery
{
    private enum FieldQueryType {
        CONTAINS,
        NOT_CONTAINS,
        IS_EQUAL,
        NOT_EQUAL,
        IS_EMPTY,
        IS_NOT_EMPTY,
        ENDS_WITH,
        DOES_NOT_END_WITH,
        MATCHES,
        DOES_NOT_MATCH,
        STARTS_WITH,
        DOES_NOT_START_WITH,
        EQUALS_IGNORES_CASE;
    }

    private class WebElementFieldRetrieverQuery implements Query
    {
        private final WebElementFieldRetriever fieldRetriever;
        private final FieldQueryType type;
        private final String value;

        WebElementFieldRetrieverQuery(WebElementFieldRetriever fieldRetriever, FieldQueryType type)
        {
            this(fieldRetriever, null, type);
        }

        WebElementFieldRetrieverQuery(WebElementFieldRetriever fieldRetriever, String value,
                FieldQueryType type)
        {
            this.fieldRetriever = fieldRetriever;
            this.type = type;
            this.value = value;
        }

        public ConditionFunction build()
        {
            switch(type)
            {
                case CONTAINS:
                    return new ContainsFunction(fieldRetriever, value);
                case NOT_CONTAINS:
                    return new FalseFunction(new ContainsFunction(fieldRetriever, value));
                case IS_EQUAL:
                    return new IsEqualFunction(fieldRetriever, value);
                case NOT_EQUAL:
                    return new FalseFunction(new IsEqualFunction(fieldRetriever, value));
                case IS_EMPTY:
                    return new IsEmptyFunction(fieldRetriever);
                case IS_NOT_EMPTY:
                    return new FalseFunction(new IsEmptyFunction(fieldRetriever));
                case ENDS_WITH:
                    return new EndsWithFunction(fieldRetriever, value);
                case DOES_NOT_END_WITH:
                    return new FalseFunction(new EndsWithFunction(fieldRetriever, value));
                case MATCHES:
                    return new MatchesFunction(fieldRetriever, value);
                case DOES_NOT_MATCH:
                    return new FalseFunction(new MatchesFunction(fieldRetriever, value));
                case STARTS_WITH:
                    return new StartsWithFunction(fieldRetriever, value);
                case DOES_NOT_START_WITH:
                    return new FalseFunction(new StartsWithFunction(fieldRetriever, value));
                case EQUALS_IGNORES_CASE:
                    return new EqualsIgnoresCaseFunction(fieldRetriever, value);
                default:
                    throw new UnsupportedOperationException("Unsupported field query type:" + type);
            }
        }
    }

    private final WebDriverQueryBuilder queryBuilder;
    private final WebElementFieldRetriever fieldRetriever;

    public WebDriverFieldQuery(WebDriverQueryBuilder queryBuilder,
            WebElementFieldRetriever fieldRetriever)
    {
        this.queryBuilder = queryBuilder;
        this.fieldRetriever = fieldRetriever;
    }

    public ExecutablePollerQuery contains(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.CONTAINS);
    }

    public ExecutablePollerQuery notContains(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.NOT_CONTAINS);
    }

    public ExecutablePollerQuery isEqual(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.IS_EQUAL);
    }

    public ExecutablePollerQuery notEqual(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.NOT_EQUAL);
    }

    public ExecutablePollerQuery isEmpty()
    {
        return createExecutablePollerQuery(FieldQueryType.IS_EMPTY);
    }

    public ExecutablePollerQuery isNotEmpty()
    {
        return createExecutablePollerQuery(FieldQueryType.IS_NOT_EMPTY);
    }

    public ExecutablePollerQuery endsWith(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.ENDS_WITH);
    }

    public ExecutablePollerQuery doesNotEndWith(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.DOES_NOT_END_WITH);
    }

    public ExecutablePollerQuery matches(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.MATCHES);
    }

    public ExecutablePollerQuery doesNotMatch(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.DOES_NOT_MATCH);
    }

    public ExecutablePollerQuery startsWith(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.STARTS_WITH);
    }

    public ExecutablePollerQuery doesNotStartWith(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.DOES_NOT_START_WITH);
    }

    public ExecutablePollerQuery equalsIgnoresCase(final String value)
    {
        return createExecutablePollerQuery(value, FieldQueryType.EQUALS_IGNORES_CASE);
    }

    private ExecutablePollerQuery createExecutablePollerQuery(FieldQueryType type)
    {
        addQuery(type);
        return new WebDriverPollerQuery.WebDriverExecutablePollerQuery(queryBuilder);
    }

    private ExecutablePollerQuery createExecutablePollerQuery(String value, FieldQueryType type)
    {
        addQuery(value, type);
        return new WebDriverPollerQuery.WebDriverExecutablePollerQuery(queryBuilder);
    }

    private void addQuery(FieldQueryType type)
    {
        queryBuilder.add(new WebElementFieldRetrieverQuery(fieldRetriever, type));
    }

    private void addQuery(String value, FieldQueryType type)
    {
        queryBuilder.add(new WebElementFieldRetrieverQuery(fieldRetriever, value, type));
    }
}
