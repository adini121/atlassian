package com.atlassian.pageobjects.elements.test;

import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.pageobjects.elements.test.pageobjects.SampleTestedProduct;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractFileBasedServerTest 
{
    public static FileBasedServer server;
    public static String rootUrl;
    public static AtlassianWebDriver driver;
    public static SampleTestedProduct product;

    @BeforeClass
    public static void startServer() throws Exception
    {
        server = new FileBasedServer();
        server.startServer();
        int port = server.getPort();

        rootUrl = "http://localhost:" + port;

        System.setProperty("baseurl.testapp", rootUrl);
        System.setProperty("http.testapp.port", String.valueOf(port));
        System.setProperty("context.testapp.path", "/");

        product = TestedProductFactory.create(SampleTestedProduct.class);
        driver = product.getTester().getDriver();
    }

    @AfterClass
    public static void stopServer() throws Exception
    {
        server.stopServer();
    }
}