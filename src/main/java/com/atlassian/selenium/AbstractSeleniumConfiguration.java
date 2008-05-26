package com.atlassian.selenium;

/**
 * An abstract implementation of the SeleniumConfigurationClass that
 * has methods that return default values for some of the configuration
 * variables
 * @since v3.12
 */
public abstract class AbstractSeleniumConfiguration implements SeleniumConfiguration {

    public int getInteractionActionWait() {
        return 400;
    }

    public String getPageLoadWait() {
        return "50000";
    }
}
