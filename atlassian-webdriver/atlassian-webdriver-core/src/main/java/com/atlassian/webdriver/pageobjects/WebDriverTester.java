package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.Tester;
import com.atlassian.webdriver.AtlassianWebDriver;

import java.util.Map;

/**
 *
 */
public interface WebDriverTester extends Tester
{
    AtlassianWebDriver getDriver();
}
