package com.atlassian.webdriver.poller.webdriver.function.element;

import com.atlassian.webdriver.element.WebElementRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class IsSelectedFunction implements ConditionFunction
{
    private final WebElementRetriever elementRetriever;

    public IsSelectedFunction(final WebElementRetriever elementRetriever)
    {
        this.elementRetriever = elementRetriever;
    }

    public Boolean apply(WebDriver from)
    {
        return elementRetriever.retrieveElement().isSelected();
    }
}
