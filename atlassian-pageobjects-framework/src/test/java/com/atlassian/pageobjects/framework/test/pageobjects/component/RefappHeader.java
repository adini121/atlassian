package com.atlassian.pageobjects.framework.test.pageobjects.component;

import com.atlassian.pageobjects.component.Header;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Represents the header of the RefApp
 */
public class RefappHeader implements Header
{
    @Inject
    protected AtlassianWebDriver driver;

    public boolean isLoggedIn()
    {
        return driver.findElement(By.id("login")).getText().equals("Logout");
    }

    public boolean isAdmin()
    {
        return isLoggedIn() && driver.findElement(By.id("user")).getText().contains("(Sysadmin)");
    }
}
