package com.atlassian.webdriver.refapp.component;

import javax.inject.Inject;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.component.Header;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.webdriver.AtlassianWebDriver;

import org.openqa.selenium.By;

/**
 *
 */
public class RefappHeader implements Header
{
    private static final By LOGIN = By.id("login");

    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    public boolean isLoggedIn()
    {
        return driver.findElement(LOGIN).getText().equals("Logout");
    }

    public boolean isAdmin()
    {
        return isLoggedIn() && driver.findElement(By.id("user")).getText().contains("(Sysadmin)");
    }

    public <M extends Page> M logout(Class<M> nextPage)
    {
        if (isLoggedIn()) {
            driver.findElement(LOGIN).click();
        }
        return HomePage.class.isAssignableFrom(nextPage) ? pageBinder.bind(nextPage) : pageBinder.navigateToAndBind(nextPage);
    }
}
