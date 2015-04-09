package com.atlassian.webdriver;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxy object for {@link org.openqa.selenium.WebDriver} object. This object monitors the calls
 * made to WebDriver and
 */
public class WebDriverProxy implements InvocationHandler
{
    public WebDriverProxy(WebDriver webDriver)
    {
        Preconditions.checkNotNull(webDriver, "The webdriver object cannot be null");
        mWebDriver = webDriver;
        imgCount = 1;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        System.out.println("- Method invoked is : " + method.getName());
        takeScreenShot(new File(IMAGE_FOLDER + File.separator + String.valueOf(imgCount) + "_" + method.getName() + "_Before"));
        Object returnObj = null;
        try
        {
             returnObj = method.invoke(mWebDriver, args);
        }
        catch(Throwable e)
        {
            System.out.println("Encountered error");
            throw e;
        }
        finally
        {
            takeScreenShot(new File(IMAGE_FOLDER + File.separator + String.valueOf(imgCount++) + "_" + method.getName() + "_After"));
        }
        return returnObj;
    }

    private void takeScreenShot(final File destFile)
    {
        try {
            if (mWebDriver instanceof TakesScreenshot) {
                TakesScreenshot shotter = (TakesScreenshot) mWebDriver;
                System.out.println("Saving screenshot to: " + destFile.getAbsolutePath());
                try {
                    File screenshot = shotter.getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(screenshot, destFile);
                } catch (IOException e) {
                    System.out.println("Could not capture screenshot to: " + destFile.getAbsolutePath() + e.getMessage());
                } catch (WebDriverException e) {
                    // This will occur in Chrome for platforms (e.g. linux) where screenshots aren't supported.
                    // Webdriver already spams the logs, so only debug log it.
                    System.out.println("Details: " + e.getMessage());
                }
            }
        }
        catch(Throwable e)
        {
            System.out.println("Ignore this error!" + e.getMessage());
        }
    }
    private int imgCount;
    private static final String IMAGE_FOLDER = "./ImgFolder/";
    private final WebDriver mWebDriver;
}
