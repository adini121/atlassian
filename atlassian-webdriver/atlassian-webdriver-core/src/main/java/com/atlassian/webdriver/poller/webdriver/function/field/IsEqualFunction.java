package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class IsEqualFunction implements ConditionFunction
{
    private final WebElementFieldRetriever retriever;
    private final String value;

    public IsEqualFunction(WebElementFieldRetriever retriever, String value)
    {
        this.retriever = retriever;
        this.value = value;
    }

    public Boolean apply(@Nullable final WebDriver from)
    {
        return retriever.retrieveField().equals(value);
    }
}
