package com.atlassian.webdriver;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * Represents the web browser, adds common helper methods on top of <tt>WebDriver</tt>
 *
 * @deprecated scheduled for removal. See https://studio.atlassian.com/browse/SELENIUM-187
 */
@Deprecated
public interface AtlassianWebDriver extends WebDriver, JavascriptExecutor, HasInputDevices
{
    /**
     * Gets the underlying <tt>WebDriver</tt>.
     * @return WebDriver
     */
    WebDriver getDriver();

    /**
     * Quits this driver, closing every associated window.
     */
    void quit();

    /**
     * Waits for condition to evaluate to true until default timeout.
     *
     * <p>
     *     If the condition does not become true within default timeout, this method will throw a TimeoutException.
     * </p>
     * @param isTrue Function that evaluates true if waiting is complete.
     */
    void waitUntil(Function<WebDriver, Boolean> isTrue);

    /**
     * Waits for condition to evaluate to true until given timeout.
     *
     * <p>
     *     If the condition does not become true within given timeout, this method will throw a TimeoutException.
     * </p>
     * @param isTrue Function that evaluates true if waiting is complete.
     * @param timeoutInSeconds Timeout in seconds to wait for condition to return true.
     */
    void waitUntil(Function<WebDriver, Boolean> isTrue, int timeoutInSeconds);

    /**
     * Writes the source of the last loaded page to the specified file.
     * @param dumpFile File to write the source to.
     */
    void dumpSourceTo(File dumpFile);

    /**
     * Saves screen shot of the browser to the specified file.
     * @param destFile File to save screen shot.
     */
    void takeScreenshotTo(File destFile);

    /**
     * Wait until element is visible within search context.
     * @param elementLocator Locator strategy for the element.
     * @param context SearchContext to use when locating.
     */
    void waitUntilElementIsVisibleAt(By elementLocator, SearchContext context);

    /**
     * Wait until element is visible on the page.
     * @param elementLocator Locator strategy for the element.
     */
    void waitUntilElementIsVisible(By elementLocator);

    /**
     * Wait until element is not visible within a search context.
     * @param elementLocator Locator strategy for the element.
     * @param context SearchContext to use when locating.
     */
    void waitUntilElementIsNotVisibleAt(By elementLocator, SearchContext context);

    /**
     * Wait until element is not visible on the page.
     * @param elementLocator Locator strategy for the element.
     */
    void waitUntilElementIsNotVisible(By elementLocator);

    /**
     * Wait until element is present within a search context.
     * @param elementLocator Locator strategy for the element.
     * @param context SearchContext to use when locating.
     */
    void waitUntilElementIsLocatedAt(By elementLocator, SearchContext context);

    /**
     * Wait until element is present on the page.
     * @param elementLocator Locator strategy for the element.
     */
    void waitUntilElementIsLocated(By elementLocator);

    /**
     * Wait until element is not present within a search context.
     * @param elementLocator Locator strategy for the element.
     * @param context Parent element to use when locating.
     */
    void waitUntilElementIsNotLocatedAt(By elementLocator, SearchContext context);

    /**
     * Wait until element is not present on the page.
     * @param elementLocator Locator strategy for the element.
     */
    void waitUntilElementIsNotLocated(By elementLocator);

    /**
     * Whether an element is present on the page.
     * @param locator Locator strategy for the element.
     * @return True if the element is present, false otherwise.
     */
    boolean elementExists(By locator);

    /**
     * Whether an element is present within a search context.
     * @param locator Locator strategy for the element.
     * @param context SearchContext to use when locating.
     * @return True if element is present, false otherwise.
     */
    boolean elementExistsAt(By locator, SearchContext context);

    /**
     * Whether an element is visible on the page.
     * @param locator Locator strategy for the element.
     * @return True if element is visible, false otherwise
     */
    boolean elementIsVisible(By locator);

    /**
     * Whether an element is visible within a given search context.
     * @param locator Locator strategy for the element.
     * @param context SearchContext to use when locating.
     * @return True if element is visible, false otherwise
     */
    boolean elementIsVisibleAt(By locator, SearchContext context);
}
