package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class IsEmptyFunction implements ConditionFunction
{
    private final WebElementFieldRetriever fieldRetriever;

    public IsEmptyFunction(WebElementFieldRetriever fieldRetriever)
    {
        this.fieldRetriever = fieldRetriever;
    }

    public Boolean apply(@Nullable final WebDriver from)
    {
        return fieldRetriever.retrieveField().isEmpty();
    }
}
