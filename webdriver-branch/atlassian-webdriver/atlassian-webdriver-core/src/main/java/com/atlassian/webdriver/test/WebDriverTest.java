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
    protected WebDriver driver;

    @BeforeClass
    protected void startWebDriver()
    {
        driver = AtlassianWebDriver.getDriver();
    }


    @AfterClass
    protected void closeSession()
    {
        AtlassianWebDriver.quitDriver();
    }

}