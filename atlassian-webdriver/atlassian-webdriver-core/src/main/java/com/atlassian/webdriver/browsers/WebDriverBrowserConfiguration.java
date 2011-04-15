package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserConfiguration;
import com.atlassian.browsers.BrowserVersion;

import java.io.File;

/**
 * TODO: Document this class / interface here
 *
 */
public class WebDriverBrowserConfiguration implements BrowserConfiguration
{
    private final File targetDir = new File("target");
    private final File webdriverDir = new File(targetDir, "webdriverTmp");

    public WebDriverBrowserConfiguration() {}

    public File getTmpDir()
    {
        return webdriverDir;
    }

    public String getBrowserName()
    {
        return System.getProperty("webdriver.browser", BrowserVersion.FIREFOX_3_5.getBrowserName());
    }
}
