package com.atlassian.webdriver.poller;

import com.atlassian.annotations.ExperimentalApi;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Exposes a set of methods to allow for polling elements for particular conditions
 *
 * <strong>WARNING</strong>: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public interface Poller
{
    PollerQuery until();
    PollerQuery until(int timeout);
    PollerQuery until(String timeoutStr);
}
