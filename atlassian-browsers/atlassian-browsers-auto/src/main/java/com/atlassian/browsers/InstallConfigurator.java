package com.atlassian.browsers;

/**
 * An abstract class which needs to be passed to the BrowserAutoInstaller.
 * For each browser type there is a setup*Browser* method which is called by the BrowserAutoInstaller
 * and takes the browserConfig and performs additional setup tasks.
 */
public abstract class InstallConfigurator
{
    abstract public void setupFirefoxBrowser(BrowserConfig browserConfig);
    abstract public void setupChromeBrowser(BrowserConfig browserConfig);
}
