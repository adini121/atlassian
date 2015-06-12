package com.atlassian.webdriver;

import com.atlassian.webdriver.utils.element.WebDriverPoller;

final class BrowserMetricsAtlassianWebDriver extends ForwardingAtlassianWebDriver
{
    private final AtlassianWebDriver webDriver;

    BrowserMetricsAtlassianWebDriver(final AtlassianWebDriver webDriver)
    {
        this.webDriver = webDriver;
    }

    @Override
    protected AtlassianWebDriver getAtlassianWebDriver()
    {
        return webDriver;
    }

    @Override
    public Navigation navigate()
    {
        return new BrowserMetricsNavigation(super.navigate(), webDriver, new WebDriverPoller(webDriver));
    }
}