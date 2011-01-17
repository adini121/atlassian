package com.atlassian.pageobjects.framework;

import com.atlassian.pageobjects.framework.TimedQuery;
import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Implementation of TimedQuery based on WebDriver
 */
public class WebDriverTimedQuery<T> implements TimedQuery<T>
{
    private final Function<WebDriver, T> actualValueFunc;
    private final WebDriver driver;
    private final int timeout;

    /**
     * Constructs a TimedQuery
     *
     * @param driver The WebDriver instance
     * @param timeout The timeout wait for the query to return the expected value
     * @param actualValueFunc Function that is capable of retrieving the acutal value of the DOM
     */
    public WebDriverTimedQuery(WebDriver driver, int timeout, final Function<WebDriver, T> actualValueFunc)
    {
        this.actualValueFunc = actualValueFunc;
        this.driver = driver;
        this.timeout = timeout;
    }

    public void waitFor(final T expected)
    {
         WebDriverWait driverWait = new WebDriverWait(driver, timeout);
            driverWait.until(new Function<WebDriver, Boolean>(){
                public Boolean apply(WebDriver webDriver) {
                    T actual = actualValueFunc.apply(webDriver);
                    return  actual.equals(expected);
                }
            });
    }
}
