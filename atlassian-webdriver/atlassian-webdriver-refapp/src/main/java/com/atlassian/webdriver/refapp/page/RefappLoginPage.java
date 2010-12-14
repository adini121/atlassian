package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.ValidateLocation;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public class RefappLoginPage extends RefappAbstractPage implements LoginPage
{
    @Inject
    PageNavigator pageNavigator;

    public String getUrl()
    {
        return "/plugins/servlet/login";
    }

    public <M extends Page> M login(User user, Class<M> nextPage)
    {
        driver.findElement(By.name("os_username")).sendKeys(user.getUsername());
        driver.findElement(By.name("os_password")).sendKeys(user.getPassword());
        driver.findElement(By.id("os_login")).submit();
        return (M) pageNavigator.gotoPage(RefappHomePage.class);
    }


    public <M extends Page> M loginAsSysAdmin(Class<M> nextPage)
    {
        return login(new User("admin", "admin", "fullname", "email"), nextPage);
    }

}
