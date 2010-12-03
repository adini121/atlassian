package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.Tester;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public interface WebDriverTester extends Tester
{
    WebDriver getDriver();
}
