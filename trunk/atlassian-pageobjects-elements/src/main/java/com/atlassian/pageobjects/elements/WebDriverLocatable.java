package com.atlassian.pageobjects.elements;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;

/**
 * <p/>
 * A SearchContext that can be located by WebDriver, capable of re-locating. A locatable consists of locator used to
 * locate itself, and the parent locatable that forms a search context, in which we locate this locatable.
 *
 * <p/>
 * Locatables form a list representing the parent-child relationship of SearchContexts. The root locatable
 * is always the locatable for WebDriver itself (think of it as a global search context).
 * To locate a SearchContext, first the parent is located then the locator is applied to it.
 */
public interface WebDriverLocatable
{
    /**
     * Gets the WebDriver locator for this SearchContext.
     *
     * @return Locator, null if root.
     */
    By getLocator();

    /**
     * The parent of this SearchContext.
     *
     * @return The locatable for the parent, null if root.
     */
    WebDriverLocatable getParent();

    /**
     * Wait until this SearchContext represented by this locatable is located.
     *
     * @param driver AtlassianWebDriver
     * @param timeoutInSeconds Timeout to wait until located, may be 0.
     * @return SearchContext
     * @throws NoSuchElementException if context could not be located before timeout expired
     */
    SearchContext waitUntilLocated(AtlassianWebDriver driver, int timeoutInSeconds) throws NoSuchElementException;

    /**
     * Whether this SearchContext is present by given <tt>timeout</tt>.
     *
     * @param driver AtlassianWebDriver
     * @param timeoutForParentInSeconds Timout to wait until parent is located
     * @return True if SearchContext is located before the timeout expires, false otherwise.
     */
    boolean isPresent(AtlassianWebDriver driver, int timeoutForParentInSeconds);
}
