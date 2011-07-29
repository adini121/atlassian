package com.atlassian.webdriver.utils;

import java.util.Locale;

public enum Browser
{
    ALL,
    FIREFOX,
    OPERA,
    SAFARI,
    UNKNOWN,
    IE,
    CHROME,
    HTMLUNIT,
    IPHONE,
    ANDROID;

    /**
     * @return a lowercase version of the name
     */
    public String getName()
    {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public static Browser typeOf(String browserStartString)
    {
        for (Browser browser : Browser.values())
        {
            if (browserStartString.startsWith(browser.getName()))
            {
                return browser;
            }
        }
        return UNKNOWN;
    }

}