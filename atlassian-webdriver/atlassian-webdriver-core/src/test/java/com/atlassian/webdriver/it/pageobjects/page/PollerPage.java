package com.atlassian.webdriver.it.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.webdriver.poller.Poller;
import org.openqa.selenium.WebDriver;

import javax.inject.Inject;

/**
 * @since 2.1.0
 */
public class PollerPage implements Page
{
    @Inject
    Poller poller;

    @Inject
    WebDriver driver;

    public String getUrl()
    {
        return "/html/poller-test-page.html";
    }

    public Poller getPoller()
    {
        return poller;
    }
}
