package com.atlassian.webdriver.test;


import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.WebDriverFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

/**
 * The base class that a Test should extend inorder to ensure that WebDriver is started up and
 * stopped correctly.
 */
@Deprecated
abstract public class WebDriverTest
{
    protected static WebDriver driver;

    @BeforeClass
    public static void startWebDriver()
    {
        driver = WebDriverFactory.getDriver();
    }

    @AfterClass
    public static void closeSession()
    {
        driver.quit();
        driver = null;
    }

}