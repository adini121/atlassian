package com.atlassian.webdriver;

import com.atlassian.pageobjects.browser.Browser;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Normally, this would be a <tt>ForwardingWebDriver</tt>, but some genius started casting a {@link
 * org.openqa.selenium.WebDriver} reference to orthogonal types in {@link com.atlassian.webdriver.DefaultAtlassianWebDriver}.
 *
 * @since v2.3.0
 */
abstract class ForwardingAtlassianWebDriver implements AtlassianWebDriver
{
    protected abstract AtlassianWebDriver getAtlassianWebDriver();

    @Override
    public void get(final String url)
    {
        getAtlassianWebDriver().get(url);
    }

    @Override
    public String getCurrentUrl()
    {
        return getAtlassianWebDriver().getCurrentUrl();
    }

    @Override
    public String getTitle()
    {
        return getAtlassianWebDriver().getTitle();
    }

    @Override
    public List<WebElement> findElements(final By by)
    {
        return getAtlassianWebDriver().findElements(by);
    }

    @Override
    public WebElement findElement(final By by)
    {
        return getAtlassianWebDriver().findElement(by);
    }

    @Override
    public String getPageSource()
    {
        return getAtlassianWebDriver().getPageSource();
    }

    @Override
    public void close()
    {
        getAtlassianWebDriver().close();
    }

    @Override
    public WebDriver getDriver()
    {
        return getAtlassianWebDriver().getDriver();
    }

    @Override
    public void quit()
    {
        getAtlassianWebDriver().quit();
    }

    @Override
    public void waitUntil(final Function<WebDriver, Boolean> isTrue)
    {
        getAtlassianWebDriver().waitUntil(isTrue);
    }

    @Override
    public void waitUntil(final Function<WebDriver, Boolean> isTrue, final int timeoutInSeconds)
    {
        getAtlassianWebDriver().waitUntil(isTrue, timeoutInSeconds);
    }

    @Override
    public void dumpSourceTo(final File dumpFile)
    {
        getAtlassianWebDriver().dumpSourceTo(dumpFile);
    }

    @Override
    public void takeScreenshotTo(final File destFile)
    {
        getAtlassianWebDriver().takeScreenshotTo(destFile);
    }

    @Override
    public void waitUntilElementIsVisibleAt(final By elementLocator, final SearchContext context)
    {
        getAtlassianWebDriver().waitUntilElementIsVisibleAt(elementLocator, context);
    }

    @Override
    public void waitUntilElementIsVisible(final By elementLocator)
    {
        getAtlassianWebDriver().waitUntilElementIsVisible(elementLocator);
    }

    @Override
    public void waitUntilElementIsNotVisibleAt(final By elementLocator, final SearchContext context)
    {
        getAtlassianWebDriver().waitUntilElementIsNotVisibleAt(elementLocator, context);
    }

    @Override
    public void waitUntilElementIsNotVisible(final By elementLocator)
    {
        getAtlassianWebDriver().waitUntilElementIsNotVisible(elementLocator);
    }

    @Override
    public void waitUntilElementIsLocatedAt(final By elementLocator, final SearchContext context)
    {
        getAtlassianWebDriver().waitUntilElementIsLocatedAt(elementLocator, context);
    }

    @Override
    public void waitUntilElementIsLocated(final By elementLocator)
    {
        getAtlassianWebDriver().waitUntilElementIsLocated(elementLocator);
    }

    @Override
    public void waitUntilElementIsNotLocatedAt(final By elementLocator, final SearchContext context)
    {
        getAtlassianWebDriver().waitUntilElementIsNotLocatedAt(elementLocator, context);
    }

    @Override
    public void waitUntilElementIsNotLocated(final By elementLocator)
    {
        getAtlassianWebDriver().waitUntilElementIsNotLocated(elementLocator);
    }

    @Override
    public boolean elementExists(final By locator)
    {
        return getAtlassianWebDriver().elementExists(locator);
    }

    @Override
    public boolean elementExistsAt(final By locator, final SearchContext context)
    {
        return getAtlassianWebDriver().elementExistsAt(locator, context);
    }

    @Override
    public boolean elementIsVisible(final By locator)
    {
        return getAtlassianWebDriver().elementIsVisible(locator);
    }

    @Override
    public boolean elementIsVisibleAt(final By locator, final SearchContext context)
    {
        return getAtlassianWebDriver().elementIsVisibleAt(locator, context);
    }

    @Override
    public Set<String> getWindowHandles()
    {
        return getAtlassianWebDriver().getWindowHandles();
    }

    @Override
    public String getWindowHandle()
    {
        return getAtlassianWebDriver().getWindowHandle();
    }

    @Override
    public TargetLocator switchTo()
    {
        return getAtlassianWebDriver().switchTo();
    }

    @Override
    public Navigation navigate()
    {
        return getAtlassianWebDriver().navigate();
    }

    @Override
    public Options manage()
    {
        return getAtlassianWebDriver().manage();
    }

    @Override
    public Browser getBrowser()
    {
        return getAtlassianWebDriver().getBrowser();
    }

    @Override
    public Keyboard getKeyboard()
    {
        return getAtlassianWebDriver().getKeyboard();
    }

    @Override
    public Mouse getMouse()
    {
        return getAtlassianWebDriver().getMouse();
    }

    @Override
    public Object executeScript(final String script, final Object... args)
    {
        return getAtlassianWebDriver().executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(final String script, final Object... args)
    {
        return getAtlassianWebDriver().executeAsyncScript(script, args);
    }

    @Override
    public WebDriver getWrappedDriver()
    {
        return getAtlassianWebDriver().getWrappedDriver();
    }
}