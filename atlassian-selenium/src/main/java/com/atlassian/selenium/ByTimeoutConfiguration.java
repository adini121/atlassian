package com.atlassian.selenium;

import com.atlassian.selenium.pageobjects.PageElement;

public class ByTimeoutConfiguration {
    private Condition condition;
    private String key;
    private long maxWaitTime;
    private long conditionCheckInterval;
    private String assertMessage;
    private boolean autoGeneratedKey;

    ByTimeoutConfiguration(Condition condition, String key, boolean autoGeneratedKey, long maxWaitTime, long conditionCheckInterval, String assertMessage)
    {
        this.condition = condition;
        this.key = key;
        this.maxWaitTime = maxWaitTime;
        this.conditionCheckInterval = conditionCheckInterval;
        this.autoGeneratedKey = autoGeneratedKey;
    }

    ByTimeoutConfiguration(Condition condition, PageElement elem, long maxWaitTime, long conditionCheckInterval, String assertMessage)
    {
        this(condition, elem.getElementKey(), e
                lem.isAutogeneratedKey(), maxWaitTime, conditionCheckInterval, assertMessage);
    }

    public Condition getCondition()
    {
        return condition;
    }

    public String getKey()
    {
        return key;
    }


    public long getMaxWaitTime()
    {
        return maxWaitTime;
    }

    public long getConditionCheckInterval()
    {
        return conditionCheckInterval;
    }

    public String getAssertMessage()
    {
        return assertMessage;
    }

    public boolean getAutoGeneratedKey()
    {
        return autoGeneratedKey;
    }
}
