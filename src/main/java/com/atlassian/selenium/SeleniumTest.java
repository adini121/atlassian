package com.atlassian.selenium;

import junit.framework.TestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * A base class for selenium tests
 *
 * @since v3.12
 */
public abstract class SeleniumTest extends TestCase {

    protected Selenium selenium;
    protected SeleniumAssertions assertThat;
    protected SeleniumInteractions interactions;
    protected SeleniumConfiguration config;

    public abstract SeleniumConfiguration getSeleniumConfiguration();


    /**
     * Calls overridden onSetup method before starting
     * the selenium client and possibly server and initiating
     * assertThat and interaction variables
     */
    public final void setUp()
    {
        config = getSeleniumConfiguration();
        selenium = SeleniumStarter.getInstance().getSeleniumClient(config);
        assertThat = new SeleniumAssertions(selenium, config.getPageLoadWait());
        interactions = new SeleniumInteractions(selenium, config.getPageLoadWait(), config.getInteractionActionWait());
        onSetUp();
    }

    /**
     * To be overridden in the case of test-specific setup activities
     */
    public void onSetUp()
    {
    }

    /**
     * Calls overridden onTearDown method before shutting down
     * the selenium client and possibly server
     */
    public final void tearDown()
    {

        onTearDown();
        if (SeleniumStarter.getInstance().isManual())
        {
            SeleniumStarter.getInstance().stop();
        }
    }

    /**
     * To be overridden in the case of test-specific tear-down activities
     */
    public  void onTearDown()
    {

    }

    public SeleniumAssertions assertThat()
    {
        return assertThat;
    }

    public SeleniumInteractions interactions()
    {
        return interactions;
    }
}
