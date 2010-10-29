package webdriver.browsers;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * Client that supports automatically installing the appropriate browser for the environment
 *
 */
public enum WebDriverBrowserAutoInstall
{
    INSTANCE;

    private AutoInstallConfiguration config;
    private AtlassianWebDriver driver;

    WebDriverBrowserAutoInstall() {
        this.config = new AutoInstallConfiguration();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                driver.quit();
            }
        });

        driver = WebDriverFactory.getDriver();

    }

    public AtlassianWebDriver getDriver()
    {
        return driver;
    }
}