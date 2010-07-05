package com.atlassian.webdriver.page;

import com.atlassian.webdriver.utils.VisibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
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
        waitUntilLocated(by, null);
    }

    public void waitUntilLocated(By by, WebElement el)
    {
        wait.until(new VisibilityOfElementLocated(by, el));
    }

    public void get(String uri, boolean activated)
    {
        if (!activated && !at(uri))
        {
            goTo(uri);
        }
    }

    public boolean at(String uri)
    {
        return driver.getCurrentUrl().equals(baseUrl + uri);
    }

    public void goTo(String uri)
    {
        driver.get(baseUrl + uri);
    }

    public WebDriver driver()
    {
        return driver;
    }

}
