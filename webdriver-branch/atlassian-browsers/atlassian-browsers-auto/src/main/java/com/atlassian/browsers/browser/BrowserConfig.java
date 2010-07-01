package com.atlassian.browsers.browser;

import java.io.File;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class BrowserConfig
{

    private File binary;
    private File profile;

    public BrowserConfig(File binary, File profile)    {
        this.binary = binary;
        this.profile = profile;
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
