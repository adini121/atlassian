package webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 */
public class WebDriverBrowserAutoInstall
{

    static
    {
        AutoInstallConfiguration config = new AutoInstallConfiguration();
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                AtlassianWebDriver.quitDriver();
            }
        });
    }

    public static WebDriver getDriver()
    {
        return AtlassianWebDriver.getDriver();
    }
}