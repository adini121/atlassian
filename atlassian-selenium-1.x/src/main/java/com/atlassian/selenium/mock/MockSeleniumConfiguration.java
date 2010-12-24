package com.atlassian.selenium.mock;

import com.atlassian.selenium.SeleniumConfiguration;

/**
 * Mock {@link com.atlassian.selenium.SeleniumConfiguration}.
 *
 * @since v1.21
 */
public class MockSeleniumConfiguration implements SeleniumConfiguration
{
    private long conditionEvalInterval = 100;

    public MockSeleniumConfiguration conditionInterval(long interval)
    {
        this.conditionEvalInterval = interval;
        return this;
    }

    public String getServerLocation()
    {
        return null;
    }

    public int getServerPort()
    {
        return 0;
    }

    public String getBrowserStartString()
    {
        return null;
    }

    public String getFirefoxProfileTemplate()
    {
        return null;
    }

    public String getBaseUrl()
    {
        return null;
    }

    public boolean getStartSeleniumServer()
    {
        return false;
    }

    public long getActionWait()
    {
        return 500;
    }

    public long getPageLoadWait()
    {
        return 40000;
    }

    public long getConditionCheckInterval()
    {
        return conditionEvalInterval;
    }

    public boolean getSingleWindowMode()
    {
        return false;
    }
}
