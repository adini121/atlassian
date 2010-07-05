package com.atlassian.webdriver.test;



import com.atlassian.webdriver.AtlassianWebDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

/**
 * The base class that a Test should extend inorder to ensure that
 * WebDriver is started up and stopped correctly.
 */
abstract public class WebDriverTest
{
    protected static WebDriver driver;

    @BeforeClass
    public static void startWebDriver()
    {
        driver = AtlassianWebDriver.getDriver();
    }


    @AfterClass
    public static void closeSession()
    {
        AtlassianWebDriver.quitDriver();
        driver = null;
    }

}