package webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class WebdriverBrowserAutoInstall
{

    static
    {
        AutoInstallConfiguration config = new AutoInstallConfiguration();
    }

    public static WebDriver getDriver()
    {
        return AtlassianWebDriver.getDriver();
    }


}