package com.atlassian.pageobjects.binder;

/**
 *
 */
public class InvalidPageStateException extends PageBindingException
{
    public InvalidPageStateException(Object pageObject, Throwable cause)
    {
        super(pageObject, cause);
    }

    public InvalidPageStateException(String message, Object pageObject)
    {
        super(message, pageObject);
    }

    public InvalidPageStateException(String message, Object pageObject, Throwable cause)
    {
        super(message, pageObject, cause);
    }
}
