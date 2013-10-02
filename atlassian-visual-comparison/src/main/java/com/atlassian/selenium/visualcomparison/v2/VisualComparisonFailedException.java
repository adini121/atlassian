package com.atlassian.selenium.visualcomparison.v2;

/**
 * TODO
 *
 * @since 2.3
 */
public class VisualComparisonFailedException extends RuntimeException
{
    // TODO: id, resolution etc.

    public VisualComparisonFailedException()
    {
    }

    public VisualComparisonFailedException(String message)
    {
        super(message);
    }

    public VisualComparisonFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public VisualComparisonFailedException(Throwable cause)
    {
        super(cause);
    }
}
