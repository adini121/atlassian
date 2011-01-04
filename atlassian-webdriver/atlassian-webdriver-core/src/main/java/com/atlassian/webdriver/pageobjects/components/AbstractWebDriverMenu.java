package com.atlassian.webdriver.pageobjects.components;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.ElementNotDisplayedException;

import javax.inject.Inject;

/**
 * Represents a collapsible menu component.
 */
public abstract class AbstractWebDriverMenu<P extends AbstractWebDriverMenu<P>>
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    private final By rootElementLocator;
    private WebElement rootElement;
    private boolean rootElementLocated = false;

    /**
     * Default constructor
     * @param rootElementLocator The locator to the root element of the menu.
     */
    public AbstractWebDriverMenu(By rootElementLocator)
    {
        this.rootElementLocator = rootElementLocator;
    }

    /**
     * Attempts to locate the root element of the menu and verifies that is visible.
     * @return The root element of the menu.
     */
    private WebElement getRootElement()
    {
        if(!rootElementLocated)
        {
            if(!isRootPresent() || !isRootVisible())
            {
                throw new ElementNotDisplayedException("Menu is not present or not visible on the page.");
            }

            rootElement = driver.findElement(rootElementLocator);
            rootElementLocated = true;
        }
        return rootElement;
    }

    /**
     * Implemented by derived class, executes the action that expands the menu.
     * @param rootElement The root element of the menu.
     */
    protected abstract void activate(WebElement rootElement);

    /**
     * My be overriden by a derived class, executes the action to collapse the menu.
     * @param rootElement The root element of the menu
     */
    protected void deactivate(WebElement rootElement)
    {
        activate(rootElement);
    }

    /**
     * Whether the root of the menu is present
     */
    public boolean isRootPresent()
    {
        return driver.elementExists(rootElementLocator);    
    }

    /**
     * Whether the root of menu is visible
     * @return
     */
    public boolean isRootVisible()
    {
        return driver.elementIsVisible(rootElementLocator); 
    }

    /**
     * Whether the menu is expanded
     */
    public boolean isOpen()
    {
        return isOpen(getRootElement());
    }

    /**
     * Implemented by derived class, checks whether the menu is expanded.
     * @param rootElement The root element of the menu
     */
    protected abstract boolean isOpen(WebElement rootElement);

    /**
     * Expands the menu
     */
    public P open()
    {
        WebElement rootElement = getRootElement();
        if(!isOpen(rootElement))
        {
            activate(rootElement);
            waitUntilOpen();
        }
        return fluentReturn();
    }

    /**
     * Waits until the menu is expanded.
     */
    public void waitUntilOpen()
    {
         driver.waitUntil(new Function<WebDriver,Boolean>(){
            public Boolean apply( WebDriver webDriver) {
                return isOpen();
            }
        });
    }

    /**
     * Waits until the menu is collapsed.
     */
    public void waitUntilClose()
    {
         driver.waitUntil(new Function<WebDriver,Boolean>(){
            public Boolean apply( WebDriver webDriver) {
                return !isOpen();
            }
        });
    }

    /**
     * Collapses the menu
     */
    public P close()
    {
        WebElement rootElement = getRootElement();
        if(isOpen(rootElement))
        {
            deactivate(rootElement);
            waitUntilClose();
        }
        return fluentReturn();
    }

    @SuppressWarnings("unchecked")
    protected P fluentReturn()
    {
        return (P) this;
    }
}
