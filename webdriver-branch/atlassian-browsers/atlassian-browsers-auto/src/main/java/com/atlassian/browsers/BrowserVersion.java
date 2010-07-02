package com.atlassian.browsers;

/**
 * Defines the available browser versions.
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

    /**
     * Determines the browser version based on the browser string passed in.
     * @param browserStr The browser string to check
     * @return The BrowserVersion enum or null if it's not found.
     */
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
