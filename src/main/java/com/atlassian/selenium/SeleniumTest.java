package com.atlassian.selenium;

import junit.framework.TestCase;

/**
 * A base class for selenium tests
 *
 * @since v3.12
 */
public abstract class SeleniumTest extends TestCase
{
    protected SeleniumAssertions assertThat;
    protected SeleniumClient client;
    protected SeleniumConfiguration config;

    public abstract SeleniumConfiguration getSeleniumConfiguration();

    /**
     * Calls overridden onSetup method before starting
     * the selenium client and possibly server and initiating
     * assertThat and interaction variables
     */
    public final void setUp() throws Exception
    {
        super.setUp();
        config = getSeleniumConfiguration();
        client = getSeleniumClient();
        assertThat = new SeleniumAssertions(client, config.getPageLoadWait());
        onSetUp();
    }

    /**
     * Gets the SeleniumClient. Override this method if you would like to return your
     * own implementation of {@link SeleniumClient}.
     * @return
     */
    protected SeleniumClient getSeleniumClient()
    {
        return SeleniumStarter.getInstance().getSeleniumClient(getSeleniumConfiguration());
    }

    /**
     * To be overridden in the case of test-specific setup activities
     */
    protected void onSetUp()
    {
    }

    /**
     * Calls overridden onTearDown method before shutting down
     * the selenium client and possibly server
     */
    public final void tearDown() throws Exception
    {
        super.tearDown();
        onTearDown();
        if (SeleniumStarter.getInstance().isManual())
        {
            SeleniumStarter.getInstance().stop();
        }
    }

    /**
     * To be overridden in the case of test-specific tear-down activities
     */
    protected  void onTearDown()
    {
    }
}
