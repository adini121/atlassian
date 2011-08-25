package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class EqualsIgnoresCaseFunction implements ConditionFunction
{
    private final WebElementFieldRetriever fieldRetriever;
    private final String value;

    public EqualsIgnoresCaseFunction(final WebElementFieldRetriever fieldRetriever, final String value)
    {
        this.fieldRetriever = fieldRetriever;
        this.value = value;
    }

    public Boolean apply(WebDriver from)
    {
        String fieldValue = fieldRetriever.retrieveField();
        return fieldValue != null && fieldValue.equalsIgnoreCase(value);
    }
}
