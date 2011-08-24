package com.atlassian.webdriver.poller.webdriver.function.element;

import com.atlassian.webdriver.element.WebElementRetriever;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.NoSuchElementException;

/**
 * @since 2.1.0
 */
public class ExistsFunction implements ConditionFunction
{
    private final WebElementRetriever elementRetriever;

    public ExistsFunction(final WebElementRetriever elementRetriever)
    {
        this.elementRetriever = elementRetriever;
    }

    public Boolean apply(@Nullable final WebDriver from)
    {
        try
        {
            // Make any call on the element.
            elementRetriever.retrieveElement().isDisplayed();
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        catch (StaleElementReferenceException e)
        {
            return false;
        }
    }
}
