package com.atlassian.webdriver.browsers;

import junit.framework.TestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class WebDriverAutoInstallerTest
{
    HelloServer server;
    String TEST_URL;

    @BeforeClass
    protected void startServer() throws Exception
    {
        server = new HelloServer();
        server.startServer();

        TEST_URL = "http://localhost:" + server.getPort() + "/";
    }

    @AfterClass
    protected void stopServer() throws Exception
    {
        server.stopServer();
    }

}
