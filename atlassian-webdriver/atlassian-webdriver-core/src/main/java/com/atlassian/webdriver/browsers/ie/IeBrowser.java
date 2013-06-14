package com.atlassian.webdriver.browsers.ie;

import com.atlassian.browsers.BrowserConfig;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;

import javax.annotation.Nullable;
import java.io.File;

/**
 *
 */
public final class IeBrowser
{

    public static final String IE_SERVICE_EXECUTABLE = "IEDriverService.exe";

    private IeBrowser()
    {
        throw new AssertionError("Don't instantiate me");
    }

    public static InternetExplorerDriver createIeDriver(@Nullable BrowserConfig config)
    {
        if (config == null || config.getProfilePath() == null)
        {
            return createDefaultDriver();
        }
        else
        {
            final InternetExplorerDriverService service = setServiceExecutablePath(config, new InternetExplorerDriverService.Builder())
                    .usingAnyFreePort()
                    .build();
            return new InternetExplorerDriver(service);
        }
    }

    public static InternetExplorerDriver createDefaultDriver()
    {
        return new InternetExplorerDriver();
    }

    private static InternetExplorerDriverService.Builder setServiceExecutablePath(BrowserConfig browserConfig, InternetExplorerDriverService.Builder builder)
    {
        if (browserConfig.getProfilePath() != null)
        {
            File profilePath = new File(browserConfig.getProfilePath());
            File ieDriverFile = new File(profilePath, IE_SERVICE_EXECUTABLE);
            if (ieDriverFile.isFile())
            {
                builder.usingDriverExecutable(ieDriverFile);
            }
        }
        return builder;
    }

}
