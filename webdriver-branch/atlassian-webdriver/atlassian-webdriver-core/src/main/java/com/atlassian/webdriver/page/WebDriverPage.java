package com.atlassian.webdriver.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The base class that a PageObject should extend.
 * It contains helper methods for interacting with a page.
 */
public abstract class WebDriverPage implements PageObject
{
    protected final WebDriver driver;
    protected final Wait<WebDriver> wait;
    private final String baseUrl;

    public WebDriverPage(WebDriver driver, String baseUrl)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
        this.baseUrl = baseUrl;
    }

    public void waitUntilLocated(By by)
    {
        wait.until(new ElementLocated(by));
    }

    public void waitUntilLocated(By by, WebElement el)
    {
        wait.until(new ElementLocated(by, el));
    }

    public void get(String uri, boolean activated)
    {
        if (!activated && !at(uri))
        {
            goTo(uri);
        }
    }

    protected boolean at(String uri)
    {
        return driver.getCurrentUrl().startsWith(baseUrl + uri);
    }

    protected void goTo(String uri)
    {
        driver.get(baseUrl + uri);
    }

    public WebDriver driver()
    {
        return driver;
    }

    public String getPageSource()
    {
        return driver.getPageSource();
    }

    abstract public boolean isLoggedIn();
    abstract public boolean isLoggedInAsUser(User user);
    abstract public boolean isAdmin();


}
