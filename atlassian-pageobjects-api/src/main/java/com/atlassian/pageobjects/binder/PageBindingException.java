package com.atlassian.pageobjects.binder;

/**
 * Parent exception for all binding exceptions
 */
public class PageBindingException extends RuntimeException
{
    private final Object pageObject;

    public PageBindingException(String message, Object pageObject)
    {
        this(message, pageObject, null);
    }

    public PageBindingException(String message, Object pageObject, Throwable cause)
    {
        super(message, cause);
        this.pageObject = pageObject;
    }

    public PageBindingException(Object pageObject, Throwable cause)
    {
        super(cause);
        this.pageObject = pageObject;
    }

    public Object getPageObject()
    {
        return pageObject;
    }
}
