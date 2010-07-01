package com.atlassian.browsers.browser;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public enum BrowserVersion
{
    FIREFOX_3_5("firefox-3.5"),
    FIREFOX_3_6("firefox-3.6"),
    CHROME_5("chrome-5");

    private String browserName;

    BrowserVersion(String name)
    {
        browserName = name;
    }

    public String getBrowserName()
    {
        return browserName;
    }

    public static BrowserVersion typeOf(String browserStr)
    {
        for (BrowserVersion browser : BrowserVersion.values())
        {
            if (browser.browserName.equals(browserStr))
            {
                return browser;
            }
        }

        return null;
    }

}
