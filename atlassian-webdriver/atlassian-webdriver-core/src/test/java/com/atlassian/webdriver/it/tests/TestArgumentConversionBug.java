package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.ArgumentConversionBugPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestArgumentConversionBug extends AbstractFileBasedServerTest
{

    ArgumentConversionBugPage argConversionBugPage;
    AtlassianWebDriver driver;

    @Before
    public void init()
    {
        argConversionBugPage = product.visit(ArgumentConversionBugPage.class);
        driver = product.getTester().getDriver();
    }

    // This test is checking that the arg processing bug has been fixed.
    // http://code.google.com/p/selenium/issues/detail?id=1280
    @Test
    public void TestDriverDoesNotFailToProcessArgs()
    {
        List<WebElement> els = driver.findElements(By.tagName("div"));
        Object[] args = new Object[] { els };
        driver.executeScript("return arguments[0] == null", args);
    }

}
