package com.atlassian.browsers;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.io.IOException;

/**
 * The default browser installer which extends InstallConfigurator.
 * This is used by the BrowserAutoInstaller and contains the client install configurator
 * This is so generic configuration can be done in the default installer and then the
 * client configurator is called to perform client specific configuration.
 */
class DefaultBrowserInstallConfigurator extends InstallConfigurator
{

    InstallConfigurator clientInstallConfigurator;

    protected DefaultBrowserInstallConfigurator(InstallConfigurator clientInstallConfigurator)
    {
        Validate.notNull(clientInstallConfigurator, "The clientInstallConfigurator cannot be null");
        this.clientInstallConfigurator = clientInstallConfigurator;
    }

    public void setupBrowser(BrowserVersion browser, BrowserConfig browserConfig)
    {
        switch (browser)
        {
            case FIREFOX_3_5:
            case FIREFOX_3_6:
                setupFirefoxBrowser(browserConfig);
                break;
            case CHROME_5:
                setupChromeBrowser(browserConfig);
                break;

            default:
                throw new RuntimeException("BrowserInstaller does not handle browser: " + browser.getBrowserName());
            }
    }

    @Override
    public void setupFirefoxBrowser(final BrowserConfig browserConfig)
    {
        clientInstallConfigurator.setupFirefoxBrowser(browserConfig);
    }

    @Override
    public void setupChromeBrowser(final BrowserConfig browserConfig)
    {
        try {
            File gch = Utils.findFile(browserConfig.getBrowserPath(), "Google Chrome Helper", false);
            Utils.make755(gch);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Unable to apply permissions to Google Chrome Helper",e);
        }
        
        clientInstallConfigurator.setupChromeBrowser(browserConfig);
    }
}
