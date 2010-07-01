package com.atlassian.browsers;

import com.atlassian.browsers.browser.BrowserInstaller;
import org.apache.commons.lang.Validate;

import java.io.File;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class BrowserAutoInstaller
{
    private static boolean useXvfb = Boolean.parseBoolean(System.getProperty("xvfb.enable", "false"));
    private XvfbManager xvfbManager;
    public static final String CHROME_XVFB = "chromeXvfb";

    private BrowserConfiguration browserConfiguration;
    private InstallConfigurator installConfigurator;

    public BrowserAutoInstaller(Class<? extends BrowserConfiguration> clazz, InstallConfigurator configurator) {

        Validate.notNull(clazz, "Browser Configuration class can not be null.");
        Validate.notNull(configurator, "The Install configurator cannot be null.");

        try {
            browserConfiguration = clazz.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        this.installConfigurator = configurator;
    }

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
