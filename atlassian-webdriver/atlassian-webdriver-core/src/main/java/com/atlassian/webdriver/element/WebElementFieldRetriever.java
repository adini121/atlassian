package com.atlassian.webdriver.element;

/**
 * @since 2.1.0
 */
public class WebElementFieldRetriever
{

    public enum FieldType
    {
        ATTRIBUTE,
        TEXT;
    }

    private final WebElementRetriever webElementRetriever;
    private final FieldType type;
    private final String name;

    public WebElementFieldRetriever(WebElementRetriever webElementRetriever, FieldType type)
    {
        this(webElementRetriever, type, null);
    }

    public WebElementFieldRetriever(WebElementRetriever webElementRetriever, FieldType type, String fieldName)
    {
        this.webElementRetriever = webElementRetriever;
        this.type = type;
        this.name = fieldName;
    }

    public String retrieveField()
    {
        switch (type)
        {
            case ATTRIBUTE:
                return webElementRetriever.retrieveElement().getAttribute(name);
            case TEXT:
                return webElementRetriever.retrieveElement().getText();
            default:
                throw new UnsupportedOperationException("Unknown webelement field retriever type: " + type);
        }
    }
}
