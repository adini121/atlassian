package com.atlassian.webdriver;

import org.openqa.selenium.WebDriver;

import java.net.URL;

abstract class ForwardingNavigation implements WebDriver.Navigation
{
    abstract WebDriver.Navigation getNavigation();

    @Override
    public void back()
    {
        getNavigation().back();
    }

    @Override
    public void forward()
    {
        getNavigation().forward();
    }

    @Override
    public void to(final String url)
    {
        getNavigation().to(url);
    }

    @Override
    public void to(final URL url)
    {
        getNavigation().to(url);
    }

    @Override
    public void refresh()
    {
        getNavigation().refresh();
    }
}