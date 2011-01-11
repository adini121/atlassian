package com.atlassian.webdriver.pageobjects.element;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;

/**
 * Base class for WebDriverElements, locates internal WebElement with a polling timeout.
 */
public abstract class AbstractWebDriverElement
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;


    protected By locator;
    protected SearchContext searchContext;
    protected int timeoutInSeconds;

    private WebElement webElement;
    private boolean webElementLocated = false;

    /**
     * Creates a WebDriverElement within the driver's search context
     *
     * @param locator The locator mechanism to use.
     *
     * @param timeoutInSeconds Timeout in seconds to use when locating the element.
     */
    public AbstractWebDriverElement(By locator, int timeoutInSeconds)
    {
        this(locator, null, timeoutInSeconds);
    }

    /**
     * Creates a WebDriverElement within a given search context
     *
     * @param locator The locator mechanism to use.
     *
     * @param searchContext The SearchContext to use.
     *
     * @param timeoutInSeconds Timeout in seconds to use when locating the element.
     */
    public AbstractWebDriverElement(By locator, SearchContext searchContext, int timeoutInSeconds)
    {
        this.locator = locator;
        this.timeoutInSeconds = timeoutInSeconds;
        this.searchContext = searchContext;
    }


    /**
     * Initializes the element, sets searchContext to top level driver if non-provided
     */
    @Init
    public void initialize()
    {
        if(searchContext == null)
        {
            searchContext = driver;
        }
    }

    /**
     * Waits until WebElement is present
     * @return The WebElement or throws exception if not found.
     */
    protected WebElement waitForWebElement()
    {
        if(!webElementLocated)
        {
            if(!driver.elementExistsAt(locator, searchContext) && timeoutInSeconds > 0)
            {
                driver.waitUntil(new ElementLocated(locator, searchContext), timeoutInSeconds);
            }

            webElement = searchContext.findElement(locator);            
            webElementLocated = true;
        }
        return webElement;
    }
}
