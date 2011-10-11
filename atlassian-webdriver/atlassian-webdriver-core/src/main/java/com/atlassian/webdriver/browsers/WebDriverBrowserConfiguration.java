package com.atlassian.webdriver.browsers;

import com.atlassian.browsers.BrowserConfiguration;
import com.atlassian.browsers.BrowserType;

import java.io.File;

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
        return System.getProperty("webdriver.browser", BrowserType.FIREFOX.getName());
    }
}
