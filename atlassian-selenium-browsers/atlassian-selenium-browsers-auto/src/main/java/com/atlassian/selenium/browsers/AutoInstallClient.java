package com.atlassian.selenium.browsers;

import com.atlassian.selenium.SeleniumAssertions;
import com.atlassian.selenium.SeleniumClient;
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

    static
    {
        File targetDir = new File("target");
        File seleniumDir = new File(targetDir, "seleniumTmp");
        seleniumDir.mkdirs();

        /* This doesn't really work since we'd have to exec selenium server to get it to pick up the new display */
        final XvfbManager xvfb = new XvfbManager(seleniumDir);
        if (useXvfb)
        {
            xvfb.start();
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    xvfb.stop();
                }
            });
        }

        AutoInstallConfiguration config = new AutoInstallConfiguration(seleniumDir, useXvfb);
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
}
