package com.atlassian.selenium.tests;

import com.atlassian.selenium.AbstractSeleniumConfiguration;

public class ParameterizedSeleniumConfiguration extends AbstractSeleniumConfiguration
{

    private final String serverLocation, browserStartString, baseUrl;
    private final int serverPort;

    public ParameterizedSeleniumConfiguration(String serverLocation, int serverPort,
                                                String browserStartString, String baseUrl)
    {
        this.serverLocation = serverLocation;
        this.serverPort = serverPort;
        this.browserStartString = browserStartString;
        this.baseUrl = baseUrl;
    }

    @Override
    public String getServerLocation() {
        return serverLocation;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public String getBrowserStartString() {
        return browserStartString;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public boolean getStartSeleniumServer() {
        return false;
    }
}
