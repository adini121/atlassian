package com.atlassian.selenium;


public enum Browser {
    FIREFOX("firefox"), OPERA("opera"), SAFARI("safari"), UNKNOWN("unkown"), IE("ie"), CHROME("chrome"), PHANTOM_JS("phantomjs");

    private final String name;

    Browser(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static Browser typeOf(String browserStartString)
    {
        for (Browser browser : Browser.values())
        {
            if(browserStartString.contains(browser.getName()))
            {
                return browser;
            }
        }
        return UNKNOWN;
    }

}
