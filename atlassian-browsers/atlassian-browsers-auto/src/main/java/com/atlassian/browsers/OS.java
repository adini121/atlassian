package com.atlassian.browsers;

/**
 * An enumeration for the Operating Systems.
 */
enum OS
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

    /**
     * Determines the OS type based on the result from OsValidator.
     * @return The OS
     * @see com.atlassian.browsers.OsValidator
     */
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
