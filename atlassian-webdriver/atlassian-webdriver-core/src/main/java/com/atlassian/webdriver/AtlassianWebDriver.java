package com.atlassian.webdriver;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 *
 */
public interface AtlassianWebDriver extends WebDriver, JavascriptExecutor
{
    WebDriver getDriver();

    void quit();

    void waitUntil(Function func);

    void waitUntil(Function func, int timeoutInSeconds);

    void dumpSourceTo(File dumpFile);

    void takeScreenshotTo(File destFile);

    void waitUntilElementIsVisibleAt(By elementLocator, WebElement at);

    void waitUntilElementIsVisible(By elementLocator);

    void waitUntilElementIsNotVisibleAt(By elementLocator, WebElement at);

    void waitUntilElementIsNotVisible(By elementLocator);

    void waitUntilElementIsLocatedAt(By elementLocator, WebElement at);

    void waitUntilElementIsLocated(By elementLocator);

    void waitUntilElementIsNotLocatedAt(By elementLocator, WebElement at);

    void waitUntilElementIsNotLocated(By elementLocator);

    boolean elementExists(By locator);

    boolean elementExistsAt(By locator, SearchContext context);

    boolean elementIsVisible(By locator);

    boolean elementIsVisibleAt(By locator, SearchContext context);

    WebElement getBody();

    void sleep(long timeout);
}
