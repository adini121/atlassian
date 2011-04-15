package com.atlassian.pageobjects.elements;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

/**
 * A SearchContext that can be located by WebDriver, capable of re-locating.
 *
 * WebDriverLocatables form a list representing the parent-child relationship of SearchContexts. The root item of the list
 * is always the locatable for WebDriver itself. To locate a SearchContext, first the parent is located then the
 * locator is applied to it.
 */
public interface WebDriverLocatable
{
    /**
     * Gets the WebDriver locator for this SearchContext
     * @return Locator, null if root.
     */
    By getLocator();

    /**
     * The parent of this SearchContext
     * @return The locatable for the parent, null if root.
     */
    WebDriverLocatable getParent();

    /**
     * Wait until this SearchContext is located
     * @param driver AtlassianWebDriver
     * @param timeoutInSeconds Timeout to wait until located.
     * @return SearchContext
     */
    SearchContext waitUntilLocated(AtlassianWebDriver driver, int timeoutInSeconds);

    /**
     * Whether this SearchContext is present
     * @param driver AtlassianWebDriver
     * @param timeoutForParentInSeconds Timout to wait until parent is located
     * @return True if SearchContext is located, false otherwise.
     */
    boolean isPresent(AtlassianWebDriver driver, int timeoutForParentInSeconds);
}
