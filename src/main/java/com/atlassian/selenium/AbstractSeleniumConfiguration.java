package com.atlassian.selenium;

/**
 * An abstract implementation of the SeleniumConfigurationClass that
 * has methods that return default values for some of the configuration
 * variables
 * @since v3.12
 */
public abstract class AbstractSeleniumConfiguration implements SeleniumConfiguration {

    public long getActionWait()
    {
        return 400;
    }

    public long getPageLoadWait()
    {
        return 50000;
    }

    public long getConditionCheckInterval()
    {
        return 100;
    }

    public String getFirefoxProfileTemplate() {
        return null;
    }
}
