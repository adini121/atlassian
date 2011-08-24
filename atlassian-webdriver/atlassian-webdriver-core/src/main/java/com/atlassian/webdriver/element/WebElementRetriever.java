package com.atlassian.webdriver.element;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * @since 2.1.0
 */
public class WebElementRetriever
{
    enum Type
    {
        LOCATOR,
        WEBELEMENT;
    }

    private final Type type;
    private By locator;
    private SearchContext context;
    private WebElement element;

    public WebElementRetriever(By locator, SearchContext context)
    {
        this.type = Type.LOCATOR;
        this.locator = locator;
        this.context = context;
    }

    public WebElementRetriever(WebElement element)
    {
        this.type = Type.WEBELEMENT;
        this.element = element;
    }

    public WebElement retrieveElement()
    {
        switch (type)
        {
            case LOCATOR:
                return context.findElement(locator);
            case WEBELEMENT:
                return element;
            default:
                throw new UnsupportedOperationException("Unknown webelement retriever type: " + type);
        }
    }
}
