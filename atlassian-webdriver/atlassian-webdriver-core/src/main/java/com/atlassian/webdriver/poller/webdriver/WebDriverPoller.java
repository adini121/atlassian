package com.atlassian.webdriver.poller.webdriver;

import com.atlassian.annotations.ExperimentalApi;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.poller.Poller;
import com.atlassian.webdriver.poller.PollerQuery;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.inject.Inject;

/**
 *
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public class WebDriverPoller implements Poller
{
    // Default wait time is 30 seconds
    private static final String DEFAULT_INTERVAL_TIME = "30s";

    private AtlassianWebDriver driver;

    @Inject
    public WebDriverPoller(AtlassianWebDriver driver)
    {
        this.driver = driver;
    }

    /**
     * Creates a {@link PollerQuery} that uses the default interval {@link #DEFAULT_INTERVAL_TIME}
     * @return
     */
    public PollerQuery until()
    {
        return until(DEFAULT_INTERVAL_TIME);
    }

    /**
     * Creates a {@link PollerQuery} which allows the timeout interval
     * for the poller to be set.
     * @param timeout in seconds for the poller to run for before failing.
     * @return a {@link PollerQuery} with the timeout interval set to the provided interval
     */
    public PollerQuery until(int timeout)
    {
        return until(timeout + "s");
    }

    /**
     * Creates a {@link PollerQuery} which setgs the timeout interval
     * to a more specific value in seconds or milliseconds.
     * @param timeoutStr A string which allows setting the timeout interval to a
     * specific value. eg. 20s or 20ms
     * @return a {@link PollerQuery} with the timeout interval set to the provided interval
     */
    public PollerQuery until(String timeoutStr)
    {
        //TODO(jwilson): implement the timeoutStr parsing
        return new WebDriverPollerQuery(new WebDriverQueryBuilder(driver, 30*1000));
    }
}
