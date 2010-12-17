package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;

import javax.inject.Inject;

public abstract class RefappAbstractPage implements Page
{
    @Inject
    protected AtlassianWebDriver driver;

    public boolean isLoggedIn()
    {
        return driver.findElement(By.id("login")).getText().equals("Logout");
    }

    public boolean isLoggedInAsUser(User user)
    {
        return isLoggedIn() && driver.findElement(By.id("user")).getText().contains(user.getFullName());
    }

    public boolean isAdmin()
    {
        return isLoggedIn() && driver.findElement(By.id("user")).getText().contains("(Sysadmin)");
    }

    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(By.className("refapp-footer"));
    }
}
