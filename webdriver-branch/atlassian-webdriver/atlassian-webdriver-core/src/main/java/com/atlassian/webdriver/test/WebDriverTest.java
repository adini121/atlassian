package com.atlassian.webdriver.test;



import com.atlassian.webdriver.AtlassianWebDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
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