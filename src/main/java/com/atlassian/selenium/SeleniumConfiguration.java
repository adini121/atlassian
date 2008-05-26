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
     * @return
     */
    String getServerLocation();

    /**
     * The port the selenium server is listening on
     * @return
     */
    int getServerPort();

    /**
     * The browser start string to be passed to Selenium 
     * @return
     */
    String getBrowserStartString();

    /**
     * The browser start string to be
     * @return
     */
    String getBaseUrl();

    /**
     * Whether the framework should start the selenium server
     * @return
     */
    boolean getStartSeleniumServer();

    /**
     * The time that various utility classes will wait after performing some Selenium
     * action like the mouseOver method in the SeleniumInteractions class.
     * @return
     */
    int getInteractionActionWait();

    /**
     * The maximum time various utility clasess will wait for a page to load.
     * Ideally this value should be used where ever a test waits for a page to load.
     * @return
     */
    String getPageLoadWait(); 
}
