package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.WebDriverPage;
import com.atlassian.webdriver.utils.QueryString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class RefappWebDriverPage extends WebDriverPage
{
    public static final String BASE_URL = System.getProperty("refapp-base-url", "http://localhost:5990/refapp");

    public RefappWebDriverPage(WebDriver driver)
    {
        super(driver, BASE_URL);
    }

    @Override
    public boolean isLoggedIn()
    {
        return driver.findElement(By.id("login")).getText().equals("Logout");
    }

    @Override
    public boolean isLoggedInAsUser(User user)
    {
        return isLoggedIn() && !driver.findElement(By.id("user")).getText().contains("(Sysadmin)");
    }

    @Override
    public boolean isAdmin()
    {
        return isLoggedIn() && driver.findElement(By.id("user")).getText().contains("(Sysadmin)");
    }

    public void login(String username, String password)
    {
        goTo("/plugins/servlet/login");
        driver.findElement(By.name("os_username")).sendKeys(username);
        driver.findElement(By.name("os_password")).sendKeys(password);
        driver.findElement(By.id("os_login")).submit();
    }
}
