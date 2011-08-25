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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    // eg. 30s or 30 s
    private final Pattern SECONDS_PATTERN = Pattern.compile("^([0-9]+)\\s*s$");
    // eg. 30ms or 30 ms
    private final Pattern MILLISECONDS_PATTERN = Pattern.compile("^([0-9]+)\\s*ms$");


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
        int timeout;

        Matcher secondsMatcher = SECONDS_PATTERN.matcher(timeoutStr);
        Matcher milliSecondsMatcher = MILLISECONDS_PATTERN.matcher(timeoutStr);

        if (secondsMatcher.matches())
        {
            timeout = Integer.parseInt(secondsMatcher.group(1)) * 1000;
        }
        else if (milliSecondsMatcher.matches())
        {
            timeout = Integer.parseInt(milliSecondsMatcher.group(1));
        }
        else
        {
            throw new IllegalArgumentException(
                    String.format("The timeout string does not match expected imput: "
                            + "eg. 15s or 10ms, but was %s", timeoutStr));
        }

        return new WebDriverPollerQuery(new WebDriverQueryBuilder(driver, timeout));
    }
}
