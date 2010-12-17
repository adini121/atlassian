package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.Tester;

import org.openqa.selenium.WebDriver;

/**
 *
 */
public interface WebDriverTester<W extends WebDriver> extends Tester
{
    W getDriver();
}
