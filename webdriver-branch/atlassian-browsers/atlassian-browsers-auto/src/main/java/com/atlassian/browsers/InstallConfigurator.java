package com.atlassian.browsers;

import com.atlassian.browsers.browser.BrowserConfig;

import java.io.File;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class InstallConfigurator
{
    abstract public void setupFirefoxBrowser(BrowserConfig browserConfig);
    abstract public void setupChromeBrowser(BrowserConfig browserConfig);
}
