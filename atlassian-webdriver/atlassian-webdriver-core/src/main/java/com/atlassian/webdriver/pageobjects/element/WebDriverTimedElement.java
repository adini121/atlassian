package com.atlassian.webdriver.pageobjects.element;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.element.ElementLocated;
import com.google.common.base.Function;
import org.openqa.selenium.*;

import javax.inject.Inject;

/**
 * Implementation of TimedElement based on WebDriver
 */
public class WebDriverTimedElement implements TimedElement
{
    @Inject
    private AtlassianWebDriver driver;

    private final int timeoutInSeconds;
    private final WebDriverDelayedElement element;

    /**
     * Create a WebDriverTimedElement with the given timeout
     * @param element The WebDriverDelayedElement that has immediate implementations for all checks.
     * @param timeoutInSeconds The timeout in seconds to use when creating the TimedQueries.
     */
    public WebDriverTimedElement(WebDriverDelayedElement element, int timeoutInSeconds)
    {
        this.element = element;
        this.timeoutInSeconds = timeoutInSeconds;
    }
    
    public TimedQuery<Boolean> isPresent()
    {
        return new WebDriverTimedQuery<Boolean>(driver, timeoutInSeconds,
                new Function<WebDriver, Boolean>(){
                    public Boolean apply(WebDriver webDriver) {
                        return element.isPresent();
                    }
        });
    }

    public TimedQuery<Boolean> isVisible()
    {
        return new WebDriverTimedQuery<Boolean>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, Boolean>(){
                    public Boolean apply(WebDriver webDriver) {
                        return element.isVisible();
                    }
        });
    }

    public TimedQuery<Boolean> hasClass(final String className)
    {
        return new WebDriverTimedQuery<Boolean>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, Boolean>(){
                    public Boolean apply(WebDriver webDriver) {
                        return element.hasClass(className);
                    }
        });
    }

    public TimedQuery<String> attribute(final String name)
    {
        return new WebDriverTimedQuery<String>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, String>(){
                    public String apply(WebDriver webDriver) {
                        return element.attribute(name);
                    }
        });
    }

    public TimedQuery<String> text()
    {
        return new WebDriverTimedQuery<String>(driver, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS,
                new Function<WebDriver, String>(){
                    public String apply(WebDriver webDriver) {
                        return element.text();
                    }
        });
    }

    public TimedQuery<String> value()
    {
        return attribute("value");
    }
}
