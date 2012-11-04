package com.atlassian.webdriver.visualcomparison;

import com.atlassian.selenium.visualcomparison.VisualComparableClient;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.common.base.Function;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * @since 2.1
 */
public class WebDriverVisualComparableClient implements VisualComparableClient
{
    private final AtlassianWebDriver driver;

    public WebDriverVisualComparableClient(final AtlassianWebDriver driver)
    {
        this.driver = driver;
    }

    public void captureEntirePageScreenshot (String filePath)
    {
        driver.takeScreenshotTo(new File(filePath));
    }

    public void evaluate (String command)
    {
        driver.executeScript(command);
    }

    public void refreshAndWait ()
    {
        // WebDriver automatically waits, or so the docs say.
        driver.navigate().refresh();
    }


    public boolean waitForJQuery (long waitTimeMillis)
    {
        // For compatibility with VisualComparableClient
        
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                String jQueryActive = ((JavascriptExecutor)webDriver).executeScript("return (window.jQuery.active)").toString();
                return (jQueryActive).equals ("0");
            }
        }, 400);
        try
        {
            Thread.sleep(waitTimeMillis);
        }
        catch (InterruptedException e)
        {
            return false;
        }
        return true;
    }
}