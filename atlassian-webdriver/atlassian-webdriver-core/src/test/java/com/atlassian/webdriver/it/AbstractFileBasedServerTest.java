package com.atlassian.webdriver.it;

import com.atlassian.webdriver.it.pageobjects.SimpleTestedProduct;
import com.atlassian.webdriver.testing.rule.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

public abstract class AbstractFileBasedServerTest 
{
    public static FileBasedServer server;
    public static String rootUrl;

    @Rule public IgnoreBrowserRule ignoreRule = new IgnoreBrowserRule();
    @Rule public TestedProductRule product = new TestedProductRule(SimpleTestedProduct.class);
    @Rule public TestBrowserRule testBrowserRule = new TestBrowserRule();
    @Rule public WebDriverScreenshotRule webDriverScreenshotRule = new WebDriverScreenshotRule();
    @Rule public SessionCleanupRule sessionCleanupRule = new SessionCleanupRule();

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
    }

    @AfterClass
    public static void stopServer() throws Exception
    {
        server.stopServer();
    }

}
