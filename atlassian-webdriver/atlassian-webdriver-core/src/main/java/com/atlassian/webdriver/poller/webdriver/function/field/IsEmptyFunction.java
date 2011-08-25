package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
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

    public Boolean apply(WebDriver from)
    {
        String fieldValue = fieldRetriever.retrieveField();
        return fieldValue == null || fieldValue.isEmpty();
    }
}
