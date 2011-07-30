package com.atlassian.webdriver.it.tests;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.ArgumentConversionBugPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TestArgumentConversionBug extends AbstractFileBasedServerTest
{

    ArgumentConversionBugPage argConversionBugPage;
    AtlassianWebDriver driver;

    @Before
    public void init()
    {
        argConversionBugPage = product.getPageBinder().navigateToAndBind(ArgumentConversionBugPage.class);
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
