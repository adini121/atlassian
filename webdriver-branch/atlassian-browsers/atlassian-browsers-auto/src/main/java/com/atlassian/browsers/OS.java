package com.atlassian.browsers;

/**
 * TODO: Document this file here
 */
public enum OS
{
    WINDOWS("windows"),
    OSX("osx"),
    LINUX("linux");

    private final String name;

    OS(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static OS getType()
    {
        if (OsValidator.isMac())
        {
            return OSX;
        }
        else if (OsValidator.isUnix())
        {
            return LINUX;
        }
        else if (OsValidator.isWindows())
        {
            return WINDOWS;
        }

        return null;
    }
}
