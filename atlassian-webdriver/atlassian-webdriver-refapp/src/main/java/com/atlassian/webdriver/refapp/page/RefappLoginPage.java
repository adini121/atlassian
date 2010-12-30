package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.Page;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 *
 */
public class RefappLoginPage extends RefappAbstractPage implements LoginPage
{
    @Inject
    PageBinder pageBinder;

    public String getUrl()
    {
        return "/plugins/servlet/login";
    }

    public <M extends Page> M login(String username, String password, Class<M> nextPage)
    {
        driver.findElement(By.name("os_username")).sendKeys(username);
        driver.findElement(By.name("os_password")).sendKeys(password);
        driver.findElement(By.id("os_login")).submit();

        return RefappHomePage.class.isAssignableFrom(nextPage) ? pageBinder.bind(nextPage) : pageBinder.navigateToAndBind(nextPage);
    }


    public <M extends Page> M loginAsSysAdmin(Class<M> nextPage)
    {
        return login("admin", "admin", nextPage);
    }

}
