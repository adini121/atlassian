package com.atlassian.webdriver.poller.webdriver.function.field;

import com.atlassian.webdriver.element.WebElementFieldRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
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

    public Boolean apply(WebDriver from)
    {
        String fieldValue = retriever.retrieveField();

        if (fieldValue == null && value == null)
        {
            return true;
        }
        else if(fieldValue != null)
        {
            return fieldValue.equals(value);
        }

        return false;
    }
}
