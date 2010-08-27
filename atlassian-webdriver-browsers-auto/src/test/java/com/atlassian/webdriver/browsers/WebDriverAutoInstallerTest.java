package com.atlassian.webdriver.browsers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class WebDriverAutoInstallerTest
{
    static HelloServer server;
    static String TEST_URL;

    @BeforeClass
    public static void startServer() throws Exception
    {
        server = new HelloServer();
        server.startServer();

        TEST_URL = "http://localhost:" + server.getPort() + "/";
    }

    @AfterClass
    public static void stopServer() throws Exception
    {
        server.stopServer();
    }

}
