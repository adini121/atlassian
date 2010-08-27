package com.atlassian.browsers;

import org.apache.commons.lang.Validate;

import java.io.File;

/**
 *
 */
public class BrowserAutoInstaller
{
    private static boolean useXvfb = Boolean.parseBoolean(System.getProperty("xvfb.enable", "false"));
    private XvfbManager xvfbManager;
    public static final String CHROME_XVFB = "chromeXvfb";

    private BrowserConfiguration browserConfiguration;
    private DefaultBrowserInstallConfigurator installConfigurator;

    /**
     * Takes a browserConfiration which defines specifics for each client and a InstallConfigurator which
     * allows post setup tasks to be executed specific for each client.
     * @param browserConfiguration
     * @param configurator
     */
    public BrowserAutoInstaller(BrowserConfiguration browserConfiguration, InstallConfigurator configurator) {

        Validate.notNull(browserConfiguration, "Browser Configuration can not be null.");
        Validate.notNull(configurator, "The Install configurator cannot be null.");

        this.browserConfiguration = browserConfiguration;
        this.installConfigurator = new DefaultBrowserInstallConfigurator(configurator);
    }

    /**
     * Setups the temporary directy to install the browser into as well as setting the dsiplay if required for
     * Unix operating systems.
     * The browser installer then installs the browser into the temporary directory.
     */
    public void setupBrowser()
    {
        File tmpDir = browserConfiguration.getTmpDir();
        tmpDir.mkdirs();
        createXvfbManager(tmpDir);

        String display = xvfbManager.getDisplay();

        if (OsValidator.isUnix())
        {
            // We use a custom browser launcher that sets the display env variable
            if (display != null)
            {
                System.setProperty("DISPLAY", display);
            }
        }

        BrowserInstaller browserInstaller = BrowserInstaller.typeOf(browserConfiguration.getBrowserName());
        browserInstaller.install(tmpDir, installConfigurator);

    }

    /**
     * Creates the xvfbManager if required.
     * @param tmpDir
     */
    private void createXvfbManager(File tmpDir)
    {
        final XvfbManager xvfb = new XvfbManager(tmpDir);
        if (useXvfb && OsValidator.isUnix())
        {
            xvfb.start();
            try
            {
                // TODO: Probably could add a more intelligent polling bit here
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                // ignore
            }
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    xvfb.stop();
                }
            });
        }

        xvfbManager = xvfb;
    }

}
