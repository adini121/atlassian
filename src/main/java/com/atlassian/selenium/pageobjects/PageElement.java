package com.atlassian.selenium.pageobjects;

import com.atlassian.selenium.SeleniumClient;

public class PageElement {
    protected final String locator;
    protected final SeleniumClient client;


    public PageElement(String locator, SeleniumClient client)
    {
        this.locator = locator;
        this.client = client;
    }

    public boolean isPresent()
    {
        return client.isElementPresent(locator);
    }

    public void click()
    {
        client.click(locator);
    }

    public String getLocator()
    {
        return locator;
    }

    public String toString()
    {
        return locator;
    }

    public String getText()
    {
        return client.getText(locator);
    }

    public int getPositionTop()
    {
        return client.getElementPositionTop(locator).intValue();
    }

    public int getPositionLeft()
    {
        return client.getElementPositionLeft(locator).intValue();
    }

    public int getWidth()
    {
        return client.getElementWidth(locator).intValue();
    }

    public int getHeight()
    {
        return client.getElementHeight(locator).intValue();
    }

}