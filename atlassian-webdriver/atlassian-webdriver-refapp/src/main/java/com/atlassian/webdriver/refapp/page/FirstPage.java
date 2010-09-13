package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FirstPage extends RefappWebDriverPage
{
    private static final String URI = "";

    public FirstPage(WebDriver driver)
    {
        super(driver);
    }

    public PageObject get(boolean activated)
    {
        super.get(URI, false);
        waitUntilLocated(By.className("refapp-footer"));
        return this;
    }
}