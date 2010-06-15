package com.atlassian.selenium.browsers;

import com.atlassian.selenium.SeleniumAssertions;
import com.atlassian.selenium.SeleniumClient;
import com.atlassian.selenium.SeleniumConfiguration;
import com.atlassian.selenium.SeleniumStarter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

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

        //final XvfbManager xvfb = new XvfbManager(seleniumDir);
        writeProperties(seleniumDir, useXvfb);
        /*if (useXvfb)
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
        */

        config = new AutoInstallConfiguration(seleniumDir, useXvfb);
        if (SeleniumStarter.getInstance().isManual())
        {
            SeleniumStarter.getInstance().start(config);
        }

        client = SeleniumStarter.getInstance().getSeleniumClient(config);
        assertThat = new SeleniumAssertions(client, config);
    }

    private static void writeProperties(File baseDir, boolean useXvfb)
    {
        try
        {
            String text = "XVFB_ENABLE=" + String.valueOf(useXvfb);
            File propFile = new File(baseDir, "xvfb.env.properties");
            FileUtils.writeStringToFile(propFile, text);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to save xvfb properties", e);
        }
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
