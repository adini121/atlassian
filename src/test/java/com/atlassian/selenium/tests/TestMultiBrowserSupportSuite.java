package com.atlassian.selenium.tests;

import com.atlassian.selenium.SeleniumConfiguration;
import com.atlassian.selenium.SeleniumMultiTestSuite;

import java.util.LinkedList;
import java.util.List;

public class TestMultiBrowserSupportSuite extends SeleniumMultiTestSuite
{
    @Override
    protected List<SeleniumConfiguration> getSeleniumConfigurations() {
        List<SeleniumConfiguration> configs = new LinkedList<SeleniumConfiguration>();
        configs.add(new ParameterizedSeleniumConfiguration("172.16.130.128", 4444,"*iexplore", "http://www.google.com/"));
        configs.add(new ParameterizedSeleniumConfiguration("172.16.130.128", 4444,"*firefox", "http://www.google.com/"));
        return configs;
    }
}
