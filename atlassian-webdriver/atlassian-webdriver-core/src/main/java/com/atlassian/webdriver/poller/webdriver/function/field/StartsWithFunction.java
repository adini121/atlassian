package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class StartsWithFunction implements ConditionFunction
{
    private final WebElementFieldRetriever fieldRetriever;
    private final String value;

    public StartsWithFunction(final WebElementFieldRetriever fieldRetriever, final String value)
    {
        this.fieldRetriever = fieldRetriever;
        this.value = value;
    }

    public Boolean apply(@Nullable final WebDriver from)
    {
        String fieldValue = fieldRetriever.retrieveField();
        return fieldValue != null && fieldValue.startsWith(value);
    }
}
