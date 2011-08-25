package com.atlassian.webdriver.it.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.Poller;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
