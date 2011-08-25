package com.atlassian.webdriver.poller.webdriver.function.element;

import com.atlassian.webdriver.element.WebElementRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.utils.Check;
import com.google.common.base.Preconditions;
import org.openqa.selenium.WebDriver;

/**
 * @since 2.1.0
 */
public class HasClassFunction implements ConditionFunction
{
    private final WebElementRetriever elementRetriever;
    private final String className;

    public HasClassFunction(WebElementRetriever elementRetriever, String className)
    {
        this.elementRetriever = elementRetriever;
        this.className = Preconditions.checkNotNull(className,
                "className cannot be null");
    }

    public Boolean apply(WebDriver from)
    {
        return Check.hasClass(className, elementRetriever.retrieveElement());
    }
}
