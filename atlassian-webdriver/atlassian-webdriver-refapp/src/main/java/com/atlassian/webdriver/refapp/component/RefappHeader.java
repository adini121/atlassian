package com.atlassian.webdriver.refapp.component;

import com.atlassian.pageobjects.page.Header;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 *
 */
public class RefappHeader implements Header
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
}
