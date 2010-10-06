package com.atlassian.webdriver.product;

import com.atlassian.webdriver.ng.product.RefappTestedProduct;

import java.net.InetAddress;

public class ProductInstance<T extends TestedProduct>
{
    public static final ProductInstance<RefappTestedProduct> REFAPP = new ProductInstance<RefappTestedProduct>(ProductType.REFAPP, "refapp", "5990", "/refapp");
    public static final ProductInstance<RefappTestedProduct> REFAPP2 = new ProductInstance<RefappTestedProduct>(ProductType.REFAPP, "refapp2", "5992", "/refapp");
    public static final ProductInstance<RefappTestedProduct> CONFLUENCE = new ProductInstance<RefappTestedProduct>(ProductType.CONFLUENCE, "confluence", "1990", "/confluence");
    public static final ProductInstance<RefappTestedProduct> CONFLUENCE2 = new ProductInstance<RefappTestedProduct>(ProductType.CONFLUENCE, "confluence2", "1992", "/confluence");
    public static final ProductInstance<RefappTestedProduct> JIRA = new ProductInstance<RefappTestedProduct>(ProductType.JIRA, "jira", "2990", "/jira");
    public static final ProductInstance<RefappTestedProduct> JIRA2 = new ProductInstance<RefappTestedProduct>(ProductType.JIRA, "jira2", "2992", "/jira");
    public static final ProductInstance<RefappTestedProduct> FECRU = new ProductInstance<RefappTestedProduct>(ProductType.FECRU, "fecru", "3990", "/fecru");
    public static final ProductInstance<RefappTestedProduct> FECRU2 = new ProductInstance<RefappTestedProduct>(ProductType.FECRU, "fecru2", "3992", "/fecru");
    public static final ProductInstance<RefappTestedProduct> BAMBOO = new ProductInstance<RefappTestedProduct>(ProductType.BAMBOO, "bamboo", "6990", "/bamboo");
    public static final ProductInstance<RefappTestedProduct> BAMBOO2 = new ProductInstance<RefappTestedProduct>(ProductType.BAMBOO, "bamboo2", "6992", "/bamboo");

    private final String baseUrl;
    private final String httpPort;
    private final String contextPath;
    private final ProductType productType;

    ProductInstance(ProductType type, final String instanceId, final String httpPortForTests, final String contextPathForTests)
    {
        this.productType = type;
        final String baseUrl = System.getProperty("baseurl." + instanceId);

        if (baseUrl != null)    // running within an AMPS IntegrationTestMojo invocation - read sys props for env vars
        {
            this.httpPort = System.getProperty("http." + instanceId + ".port");
            this.contextPath = System.getProperty("context." + instanceId + ".path");
            this.baseUrl = "http://" + getLocalHostName() + ":" + httpPort + contextPath;
        }
        else                    // running outside of AMPS (e.g. from IDE), need to set default env vars
        {
            this.httpPort = httpPortForTests;
            this.contextPath = contextPathForTests;
            this.baseUrl = "http://" + getLocalHostName() + ":" + httpPort + contextPath;
        }
    }

    public static String getLocalHostName()
    {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public String getHttpPort()
    {
        return httpPort;
    }

    public String getContextPath()
    {
        return contextPath;
    }

    public ProductType getProductType()
    {
        return productType;
    }
}
