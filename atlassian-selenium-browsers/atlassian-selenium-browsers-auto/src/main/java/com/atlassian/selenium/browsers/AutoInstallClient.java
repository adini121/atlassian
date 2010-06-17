package com.atlassian.selenium.browsers;

import com.atlassian.selenium.SeleniumAssertions;
import com.atlassian.selenium.SeleniumClient;
import com.atlassian.selenium.SeleniumConfiguration;
import com.atlassian.selenium.SeleniumStarter;

import java.io.File;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 * @since 2.0
 */
public class AutoInstallClient
{
    private static final SeleniumClient client;
    private static boolean useXvfb = Boolean.parseBoolean(System.getProperty("xvfb.enable", "false"));

    private static SeleniumAssertions assertThat;
    private static AutoInstallConfiguration config;

    static
    {
        File targetDir = new File("target");
        File seleniumDir = new File(targetDir, "seleniumTmp");
        seleniumDir.mkdirs();

        final XvfbManager xvfb = new XvfbManager(seleniumDir);
        if (useXvfb)
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

        config = new AutoInstallConfiguration(seleniumDir, xvfb.getDisplay());
        if (SeleniumStarter.getInstance().isManual())
        {
            SeleniumStarter.getInstance().start(config);
        }

        client = SeleniumStarter.getInstance().getSeleniumClient(config);
        assertThat = new SeleniumAssertions(client, config);
    }

    public static SeleniumClient seleniumClient()
    {
        return client;
    }

    public static SeleniumAssertions assertThat()
    {
        return assertThat;
    }

    public static SeleniumConfiguration seleniumConfiguration()
    {
        return config;
    }
}
