package com.atlassian.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * All util implementations should extend from this class to get access to the
 * seleniumclient and environment data.
 */
public abstract class AbstractSeleniumUtil
{

    /**
     * The maximum page load wait time used by Selenium. Should be used
     * by tests whenever they wait for pages to load.
     */
    protected final String PAGE_LOAD_WAIT;

    /**
     * A member variable back to the Selenium interface
     */
    protected Selenium selenium;
    
    public AbstractSeleniumUtil(Selenium selenium, String pageLoadWait)
    {
        this.selenium = selenium;
        PAGE_LOAD_WAIT = pageLoadWait;
    }

    public Selenium getSelenium()
    {
        return selenium;
    }
}
