package com.atlassian.webdriver;

import com.atlassian.webdriver.utils.element.WebDriverPoller;
import com.atlassian.webdriver.waiter.webdriver.function.ConditionFunction;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import javax.annotation.Nullable;

final class BrowserMetricsNavigation extends ForwardingNavigation
{
    private final WebDriver.Navigation navigation;
    private final JavascriptExecutor javascript;
    private final WebDriverPoller poller;

    BrowserMetricsNavigation(
            final WebDriver.Navigation navigation,
            final JavascriptExecutor javascript,
            final WebDriverPoller poller)
    {
        this.navigation = navigation;
        this.javascript = javascript;
        this.poller = poller;
    }

    @Override
    WebDriver.Navigation getNavigation()
    {
        return navigation;
    }

    @Override
    public void to(final String url)
    {
        super.to(url);
        waitForBrowserMetricsNavigation();
    }

    private void waitForBrowserMetricsNavigation()
    {
        if (isNavigationInProgress())
        {
            waitForBrowserMetricsToFinishNavigation();
        }
    }

    private boolean isNavigationInProgress()
    {
        return "waiting".equals(getNavigationState());
    }

    private String getNavigationState()
    {
        return javascript.executeScript("AJS.Meta.get(\"browser-metrics-navigation\")").toString();
    }

    private void waitForBrowserMetricsToFinishNavigation()
    {
        poller.waitUntil(new ConditionFunction()
        {
            @Nullable
            @Override
            public Boolean apply(final WebDriver input)
            {
                return "done".equals(getNavigationState());
            }
        });
    }

    @Override
    public void to(final URL url)
    {
        super.to(url);
        waitForBrowserMetricsNavigation();
    }
}