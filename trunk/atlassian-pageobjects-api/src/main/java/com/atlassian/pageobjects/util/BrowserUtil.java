package com.atlassian.pageobjects.util;

import com.atlassian.pageobjects.Browser;

/**
 * Utilities for manipulating the current browser.
 */
public class BrowserUtil
{
    private static Browser currentBrowser;

    public static void setCurrentBrowser(Browser browser)
    {
        currentBrowser = browser;
    }

    public static Browser getCurrentBrowser()
    {
        return currentBrowser;
    }
}
