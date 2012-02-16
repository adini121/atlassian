package com.atlassian.pageobjects;

/**
 * The product instance being tested
 */
public interface ProductInstance
{
    /**
     * @return The base URL of the instance.  Cannot be null.
     */
    String getBaseUrl();

    /**
     * @return The HTTP port
     */
    int getHttpPort();

    /**
     * @return The context path, starting with "/".  Cannot be null.
     */
    String getContextPath();

    /**
     * @return The instance id.  Cannot be null.
     */
    String getInstanceId();
}
