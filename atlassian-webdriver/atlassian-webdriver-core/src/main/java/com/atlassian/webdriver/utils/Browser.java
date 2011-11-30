package com.atlassian.webdriver.utils;

import java.util.Locale;

public enum Browser
{
    ALL,
    FIREFOX,
    OPERA,
    /** Safari on the desktop, not iOS. */
    SAFARI,
    UNKNOWN,
    IE,
    CHROME,
    /** HtmlUnit with javascript disabled. */
    HTMLUNIT_NOJS,
    /** HtmlUnit with javascript enabled. */
    HTMLUNIT,
    /** Uses a real live iPhone, iPad or other iOS device. */
    IPHONE,
    /** Uses a real live Android phone, tablet or other android device. */
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
