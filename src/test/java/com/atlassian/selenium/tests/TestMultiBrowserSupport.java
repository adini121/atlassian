package com.atlassian.selenium.tests;

import com.atlassian.selenium.ParameterizedSeleniumConfiguration;
import com.atlassian.selenium.SeleniumConfiguration;
import com.atlassian.selenium.SeleniumMultiTest;

import java.util.LinkedList;
import java.util.List;

public class TestMultiBrowserSupport extends SeleniumMultiTest
{
    @Override
    public List<SeleniumConfiguration> getSeleniumConfigurations() {
        List<SeleniumConfiguration> configs = new LinkedList<SeleniumConfiguration>();
        // The three below are the spike environment
        configs.add(new ParameterizedSeleniumConfiguration("172.20.6.110", 4444,"*iexplore", "http://www.google.com/"));
        configs.add(new ParameterizedSeleniumConfiguration("172.20.6.202", 4444,"*firefox", "http://www.google.com/"));
        configs.add(new ParameterizedSeleniumConfiguration("passionpop", 4444, "*safari", "http://www.google.com/"));
        return configs;
    }

    public void testBasicFunctionality()
    {
        client.open("");
        assertTrue(client.getTitle().equals("Google") );
        System.out.println("TestMultiBrowserSupport.testBasicFunctionality");
    }
}
