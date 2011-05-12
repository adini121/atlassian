package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.elements.query.AbstractTimedQuery;
import com.atlassian.pageobjects.elements.query.ExpirationHandler;
import com.atlassian.pageobjects.elements.query.PollingQuery;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.hamcrest.StringDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.TimeoutException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.atlassian.pageobjects.elements.query.Poller.waitUntil;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Creates WebDriveLocatables for different search strategies
 */
public class WebDriverLocators
{
    /**
     * Creates the root of a WebDriverLocatable list, usually the instance of WebDriver
     * @return WebDriverLocatable
     */
    public static WebDriverLocatable root()
    {
        return new WebDriverRootLocator();
    }

    /**
     * Creates a WebDriverLocatable for a single element
     * @param locator The locator strategy within the parent
     * @param parent The locatable for the parent
     * @return WebDriverLocatable
     */
    public static WebDriverLocatable single(By locator, WebDriverLocatable parent)
    {
        return new WebDriverSingleLocator(locator, parent);
    }

    /**
     * Creates a WebDriverLocatable for an element included in a list initialized with given element
     * @param element WebElement
     * @param locator The locator strategy within the parent that will produce a list of matches
     * @param locatorIndex The index within the list of matches to find this element
     * @param parent The locatable for the parent
     * @return WebDriverLocatable
     */
    public static WebDriverLocatable list(WebElement element, By locator, int locatorIndex, WebDriverLocatable parent)
    {
        return new WebDriverListLocator(element,locator, locatorIndex, parent);
    }

    /**
     * Whether the given WebElement is stale (needs to be relocated)
     * @param webElement WebElement
     * @return True if element reference is stale, false otherwise
     */
    public static boolean isStale(final WebElement webElement)
    {
        try
        {
            webElement.getTagName();
            return false;
        }
        catch (StaleElementReferenceException ignored)
        {
            return true;
        }
    }

    private static class WebDriverRootLocator implements WebDriverLocatable
    {
        public By getLocator()
        {
            return null;
        }

        public WebDriverLocatable getParent()
        {
            return null;
        }

        public SearchContext waitUntilLocated(AtlassianWebDriver driver, int timeoutInSeconds)
        {
            return driver;
        }

        public boolean isPresent(AtlassianWebDriver driver, int timeoutForParentInSeconds)
        {
            return true;
        }
    }

    private static class WebDriverSingleLocator implements  WebDriverLocatable
    {
        private WebElement webElement = null;
        private boolean webElementLocated = false;

        private final By locator;
        private final WebDriverLocatable parent;

        public WebDriverSingleLocator(By locator, WebDriverLocatable parent)
        {
            this.locator = locator;
            this.parent = parent;
        }

        public By getLocator()
        {
            return locator;
        }

        public WebDriverLocatable getParent()
        {
            return parent;
        }

        public SearchContext waitUntilLocated(AtlassianWebDriver driver, int timeoutInSeconds)
        {
            if(!webElementLocated || WebDriverLocators.isStale(webElement))
            {
                SearchContext searchContext = parent.waitUntilLocated(driver, timeoutInSeconds);

                if(!driver.elementExistsAt(locator, searchContext) && timeoutInSeconds > 0)
                {
                    try
                    {
                        driver.waitUntil(new ElementLocated(locator, searchContext), timeoutInSeconds);
                    }
                    catch(TimeoutException e)
                    {
                        throw new org.openqa.selenium.NoSuchElementException(new StringDescription()
                                .appendText("Unable to locate element after timeout.")
                                .appendText("\nLocator: ").appendValue(locator)
                                .appendText("\nTimeout: ").appendValue(timeoutInSeconds).appendText(" seconds.")
                                .toString(), e);
                    }
                }
                webElement = searchContext.findElement(locator);
                webElementLocated = true;
            }

            return webElement;
        }

        public boolean isPresent(AtlassianWebDriver driver, int timeoutForParentInSeconds)
        {
            return driver.elementExistsAt(this.locator, parent.waitUntilLocated(driver, timeoutForParentInSeconds));
        }

    }

    private static class WebDriverListLocator implements  WebDriverLocatable
    {
        private WebElement webElement = null;

        private final By locator;
        private final int locatorIndex;
        private final WebDriverLocatable parent;

        public WebDriverListLocator(WebElement element, By locator, int locatorIndex, WebDriverLocatable parent)
        {
            this.webElement = element;
            this.locatorIndex = locatorIndex;
            this.locator = locator;
            this.parent = parent;
        }

        public By getLocator()
        {
            return null;
        }

        public WebDriverLocatable getParent()
        {
            return parent;
        }

        public SearchContext waitUntilLocated(final AtlassianWebDriver driver, int timeoutInSeconds)
        {
            if(WebDriverLocators.isStale(webElement))
            {
                try
                {
                    webElement = waitUntil(queryForElement(driver, TimeUnit.SECONDS.toMillis(timeoutInSeconds)),
                            notNullValue(WebElement.class));
                }
                catch (AssertionError notLocated)
                {
                    throw new NoSuchElementException(new StringDescription()
                            .appendText("Unable to locate element in collection.")
                            .appendText("\nLocator: ").appendValue(locator)
                            .appendText("\nLocator Index: ").appendValue(locatorIndex)
                            .toString());
                }
            }
            return webElement;
        }

        private TimedQuery<WebElement> queryForElement(final AtlassianWebDriver driver, long timeout) {
            return new AbstractTimedQuery<WebElement>(timeout, PollingQuery.DEFAULT_INTERVAL, ExpirationHandler.RETURN_NULL)
            {
                @Override
                protected boolean shouldReturn(WebElement currentEval) {
                    return true;
                }

                @Override
                protected WebElement currentValue() {
                    // we want the parent to be located and then the child list to be long enough to contain our index!
                    if (parent.isPresent(driver, 0)) {
                        List<WebElement> webElements = parent.waitUntilLocated(driver, 0).findElements(locator);
                        return locatorIndex < webElements.size() ? webElements.get(locatorIndex) : null;
                    }
                    return null;
                }
            };
        }

        public boolean isPresent(AtlassianWebDriver driver, int timeoutForParentInSeconds)
        {
            SearchContext searchContext = parent.waitUntilLocated(driver, timeoutForParentInSeconds);

            List<WebElement> webElements = searchContext.findElements(this.locator);
            return locatorIndex <= webElements.size() - 1;
        }
    }
}
