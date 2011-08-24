package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class EndsWithFunction implements ConditionFunction
{
    private final WebElementFieldRetriever fieldRetriever;
    private final String value;

    public EndsWithFunction(WebElementFieldRetriever fieldRetriever, String value)
    {
        this.fieldRetriever = fieldRetriever;
        this.value = value;
    }

    public Boolean apply(@Nullable final WebDriver from)
    {
        return fieldRetriever.retrieveField().endsWith(value);
    }
}
