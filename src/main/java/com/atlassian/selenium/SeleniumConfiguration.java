package com.atlassian.selenium;

/**
 * A  class that should be implemented by all users of the Atlassian-Selenium library.
 * Implementations
 *
 * @since v3.12
 */
public interface SeleniumConfiguration
{
    /**
     * The address of the node hosting the selenium
     * server.
     */
    String getServerLocation();

    /**
     * The port the selenium server is listening on
     */
    int getServerPort();

    /**
     * The browser start string to be passed to Selenium 
     */
    String getBrowserStartString();

    /**
     * Gets firefox profile location
     */
    String getFirefoxProfileTemplate();

    /**
     * The starting url for the browser
     */
    String getBaseUrl();

    /**
     * Whether the framework should start the selenium server
     */
    boolean getStartSeleniumServer();

    /**
     * The time that various utility classes will wait after performing some Selenium
     * action like the mouseOver method in the {@link SeleniumClient}.
     */
    long getActionWait();

    /**
     * The maximum time various utility clasess will wait for a page to load.
     * Ideally this value should be used where ever a test waits for a page to load.
     */
    long getPageLoadWait();

    /**
     * The time in milliseconds between condition checks.
     * @see {@link SeleniumAssertions#byTimeout(Condition)}
     */
    long getConditionCheckInterval();
}
