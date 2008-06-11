package com.atlassian.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.server.SeleniumServer;

/**
 * Helper class to setup the Selenium Proxy and client.
 */
public class SeleniumStarter
{
    private static final Logger log = Logger.getLogger(SeleniumStarter.class);

    private static SeleniumStarter instance = new SeleniumStarter();
    private SeleniumClient client;
    private SeleniumServer server;
    private String userAgent;
    private boolean manual = true;

    private SeleniumStarter()
    {
    }

    public static SeleniumStarter getInstance()
    {
        return instance;
    }

    public synchronized SeleniumClient getSeleniumClient(SeleniumConfiguration config)
    {
        if (client == null)
        {
            client = new SeleniumClient(config);
        }
        return client;
    }

    public synchronized SeleniumServer getSeleniumServer(SeleniumConfiguration config)
    {
        if (server == null)
        {
            try
            {
                server = new SeleniumServer(config.getServerPort());
            } catch (Exception e)
            {
                log.error("Error creating SeleniumServer!", e);
            }
        }
        return server;
    }


    public void start(SeleniumConfiguration config)
    {
        log.info("Starting Selenium");

        try
        {
            if(config.getStartSeleniumServer())
            {
                log.info("Starting Selenium Server");
                SeleniumServer.setDebugMode(true);
                getSeleniumServer(config).start();
                log.info("Selenium Server Started");
            }
            else
            {
                log.info("Not starting Selenium Server");
            }

            log.info("Starting Selenium Client");
            getSeleniumClient(config).start();
            log.info("Selenium Client Started");

        } catch (Exception e)
        {
            log.error("Error starting SeleniumServer!", e);
        }
        
        log.info("Selenium startup complete");
    }

    public void stop()
    {
        client.stop();
        if(server != null)
        {
            server.stop();
        }
    }

    public boolean isManual()
    {
        return manual;
    }

    public void setManual(boolean manual)
    {
        this.manual = manual;
    }

    /**
     * Returns the user agent that this test is running inside
     *
     * @return one of the following
     *         <ul>
     *         <li>opera</li>
     *         <li>ie</li>
     *         <li>firefox</li>
     *         <li>safari</li>
     *         <li>unknown</li>
     *         </ul>
     */
    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }
}
