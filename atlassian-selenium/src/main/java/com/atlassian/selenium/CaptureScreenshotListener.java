package com.atlassian.selenium;

import org.apache.commons.lang.StringUtils;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * A listener that captures screenshots if a test fails. Works currently only with FireFox.
 *
 * @since 2.0
 */
public class CaptureScreenshotListener extends RunListener
{
    public CaptureScreenshotListener()
    {
        super();
    }

    @Override
    public void testFailure(final Failure failure) throws Exception
    {
        //This is the output directory configured for the maven surefire plugin.
        final String reportsDirectory = System.getProperty("reportsDirectory");
        if (!StringUtils.isEmpty(reportsDirectory))
        {
            try
            {
                //Can throw NPE, if selenium client is not initialised yet.
                final SeleniumClient seleniumClient = SeleniumStarter.getInstance().getSeleniumClient((SeleniumConfiguration) null);
                if (seleniumClient.getBrowser().equals(Browser.FIREFOX))
                {
                    //Default background color is WHITE
                    seleniumClient.captureEntirePageScreenshot(reportsDirectory + "/" + createSreenshotFileName(failure.getDescription()), "background=#FFFFFF");
                }
            }
            catch (Exception e)
            {
                //ignored
            }
        }
    }

    private String createSreenshotFileName(Description description)
    {
        final String displayName = description.getDisplayName();
        int startIndex = displayName.indexOf("(");
        int endIndex = displayName.lastIndexOf(")");
        final String filename;
        if (startIndex != -1 && endIndex != -1)
        {
            filename = displayName.substring(startIndex + 1, endIndex);
        }
        else
        {
            filename = description.getDisplayName();
        }
        return filename + ".png";
    }
}