package com.atlassian.selenium;

import junit.framework.TestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * TODO: Document this class / interface here
 *
 * @since v3.12
 */
public abstract class SeleniumTest extends TestCase {

    protected Selenium selenium;
    protected SeleniumAssertions assertThat;
    protected SeleniumInteractions interactions;
    protected SeleniumConfiguration config;

    public abstract SeleniumConfiguration getSeleniumConfiguration();


    public final void setUp()
    {
        config = getSeleniumConfiguration();
        selenium = SeleniumStarter.getInstance().getSeleniumClient(config);
        assertThat = new SeleniumAssertions(selenium, config.getPageLoadWait());
        interactions = new SeleniumInteractions(selenium, config.getPageLoadWait(), config.getInteractionActionWait());
        setUpTest();
    }

    /**
     * To be overridden in the case of test-specific setup activities
     */
    public void setUpTest()
    {
    }

    public final void tearDown()
    {

        if (SeleniumStarter.getInstance().isManual())
        {
            SeleniumStarter.getInstance().stop();
        }
    }

    /**
     * To be overridden in the case of test-specific tear-down activities
     */
    public  void tearDownTest()
    {

    }
}
