package com.atlassian.pageobjects.util;

import com.atlassian.pageobjects.browser.Browser;
import com.google.common.base.Supplier;

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

    public static Supplier<Browser> currentBrowserSupplier()
    {
        return new Supplier<Browser>()
        {
            @Override
            public Browser get()
            {
                return getCurrentBrowser();
            }
        };
    }
}
