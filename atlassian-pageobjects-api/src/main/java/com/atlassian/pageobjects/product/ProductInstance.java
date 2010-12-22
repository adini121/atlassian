package com.atlassian.pageobjects.product;

/**
 * Information representing the product instance being tested
 */
public class ProductInstance
{
    private final String baseUrl;
    private final String instanceId;
    private final int httpPort;
    private final String contextPath;


    public ProductInstance(final String instanceId, final int httpPort, final String contextPath, final String baseUrl)
    {
        this.instanceId = instanceId;
        this.httpPort = httpPort;
        this.contextPath = contextPath;
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl()
    {
        return baseUrl.toLowerCase();
    }

    public int getHttpPort()
    {
        return httpPort;
    }

    public String getContextPath()
    {
        return contextPath;
    }

    public String getInstanceId() {
        return instanceId;
    }
}