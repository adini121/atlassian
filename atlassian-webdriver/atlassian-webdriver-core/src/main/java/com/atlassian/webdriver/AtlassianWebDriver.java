package com.atlassian.webdriver;

import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.by.ByHelper;
import com.atlassian.webdriver.utils.by.ByJquery;
import com.atlassian.webdriver.utils.by.DeferredBy;
import com.atlassian.webdriver.utils.element.ElementIsVisible;
import com.atlassian.webdriver.utils.element.ElementLocated;
import com.atlassian.webdriver.utils.element.ElementNotLocated;
import com.atlassian.webdriver.utils.element.ElementNotVisible;
import com.google.common.base.Function;
import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Exposes a set of common functions to use.
 */
public class AtlassianWebDriver implements WebDriver, JavascriptExecutor
{

    private static final Logger log = LoggerFactory.getLogger(AtlassianWebDriver.class);

    private final WebDriver driver;
    private static final int WAIT_TIME = 60;

    AtlassianWebDriver(WebDriver driver)
    {
        this.driver = driver;
        ByJquery.init(driver);
        DeferredBy.init(driver);
    }

    public WebDriver getDriver()
    {
        return this.driver;
    }

    public void quit()
    {
        driver.quit();
    }

    public void waitUntil(final Function func)
    {
        new WebDriverWait(getDriver(), WAIT_TIME).until(func);
    }

    public void dumpSourceTo(File dumpFile) {
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter(dumpFile);
            IOUtils.write(getDriver().getPageSource(), fileWriter);
        }
        catch (IOException e1)
        {
            log.info("Error dumping page source to file: " + dumpFile + " : " + e1.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(fileWriter);
        }
    }

    public void takeScreenshotTo(File destFile) {
        if (getDriver() instanceof TakesScreenshot) {
            TakesScreenshot shotter = (TakesScreenshot) getDriver();
            log.info("Saving screenshot to: " + destFile.getAbsolutePath());
            File screenshot = shotter.getScreenshotAs(OutputType.FILE);
            try
            {
                IOUtil.copyCompletely(new FileInputStream(screenshot), new FileOutputStream(destFile));
            }
            catch (IOException e)
            {
                log.warn("Could not capture screenshot to: " + destFile, e);
            }
        }
    }

    /**
     * Wait utilities
     */

    public void waitUntilElementIsVisibleAt(By elementLocator, WebElement at)
    {
        waitUntil(new ElementIsVisible(elementLocator, at));
    }

    public void waitUntilElementIsVisible(By elementLocator)
    {
        waitUntilElementIsVisibleAt(elementLocator, null);
    }

    public void waitUntilElementIsNotVisbleAt(By elementLocator, WebElement at)
    {
        waitUntil(new ElementNotVisible(elementLocator, at));
    }

    public void waitUntilElementIsNotVisible(By elementLocator)
    {
        waitUntilElementIsNotVisbleAt(elementLocator, null);
    }

    public void waitUntilElementIsLocatedAt(By elementLocator, WebElement at)
    {
        waitUntil(new ElementLocated(elementLocator, at));
    }

    public void waitUntilElementIsLocated(By elementLocator)
    {
        waitUntilElementIsLocatedAt(elementLocator, null);
    }

    public void waitUntilElementIsNotLocatedAt(By elementLocator, WebElement at)
    {
        waitUntil(new ElementNotLocated(elementLocator, at));
    }

    public void waitUntilElementIsNotLocated(By elementLocator)
    {
        waitUntilElementIsNotLocatedAt(elementLocator, null);
    }

    /**
     * Check Methods
     */

    public boolean elementExists(By locator)
    {
        return Check.elementExists(locator, driver);
    }

    public boolean elementExistsAt(By locator, SearchContext context)
    {
        return Check.elementExists(locator, context);
    }

    public boolean elementIsVisible(By locator)
    {
        return Check.elementIsVisible(locator, driver);
    }

    public boolean elementIsVisibleAt(By locator, SearchContext context)
    {
        return Check.elementIsVisible(locator, context);
    }

    public WebElement getBody()
    {
        return ByHelper.getBody(driver);
    }

    public void sleep(final long timeout)
    {
        try
        {
            Thread.sleep(timeout);
        }
        catch (InterruptedException e)
        {
            // Do nothing.
        }
    }

    /**
     * WebDriver implementation below
     */

    public void get(final String url)
    {
        driver.get(url);
    }

    public String getCurrentUrl()
    {
        return driver.getCurrentUrl();
    }

    public String getTitle()
    {
        return driver.getTitle();
    }

    public List<WebElement> findElements(final By by)
    {
        return driver.findElements(by);
    }

    public WebElement findElement(final By by)
    {
        return driver.findElement(by);
    }

    public String getPageSource()
    {
        return driver.getPageSource();
    }

    public void close()
    {
        driver.close();
    }

    public Set<String> getWindowHandles()
    {
        return driver.getWindowHandles();
    }

    public String getWindowHandle()
    {
        return driver.getWindowHandle();
    }

    public TargetLocator switchTo()
    {
        return driver.switchTo();
    }

    public Navigation navigate()
    {
        return driver.navigate();
    }

    public Options manage()
    {
        return driver.manage();
    }

    public Object executeScript(final String script, final Object... args)
    {
        return ((JavascriptExecutor)driver).executeScript(script, args);
    }

    public boolean isJavascriptEnabled()
    {
        return ((JavascriptExecutor)driver).isJavascriptEnabled();
    }
}