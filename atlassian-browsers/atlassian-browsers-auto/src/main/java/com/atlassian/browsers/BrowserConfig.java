package com.atlassian.browsers;

import java.io.File;

/**
 * Contains the config for the browser such as the path to the binary to execute to start the browser
 * as well as the path to the profile for the browser.
 */
public class BrowserConfig
{
    private final File browserPath;
    private final File binary;
    private final File profile;

    protected BrowserConfig(File browserPath, File binary, File profile)    {
        this.browserPath = browserPath;
        this.binary = binary;
        this.profile = profile;
    }

    public File getBrowserPath()
    {
        return browserPath;
    }

    public String getBinaryPath()
    {
        return binary.getAbsolutePath();
    }

    public String getProfilePath()
    {
        return profile.getAbsolutePath();
    }
}
