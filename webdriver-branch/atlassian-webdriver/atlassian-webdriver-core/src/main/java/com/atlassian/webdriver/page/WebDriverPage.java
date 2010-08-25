package com.atlassian.webdriver.page;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.utils.QueryString;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The base class that a PageObject should extend. It contains helper methods for interacting with a
 * page.
 */
public abstract class WebDriverPage implements PageObject
{
    private static final long CHROME_HACK_SLEEP = 100;

    protected final WebDriver driver;
    protected final Wait<WebDriver> wait;
    protected QueryString queryString;

    private final String baseUrl;

    public WebDriverPage(WebDriver driver, String baseUrl)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
        this.baseUrl = baseUrl;
    }

    /**
     * Query string can only be set once per page. If you try to set it twice the second time then
     * an exception will be thrown.
     */
    final public void setQueryString(QueryString queryString)
    {
        Validate.isTrue(this.queryString == null);

        this.queryString = queryString;
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

        if (activated && !at(uri))
        {
            throw new IllegalStateException("Expected to be at uri: " + (baseUrl + uri) + ", instead at: " + driver.getCurrentUrl());
        }

    }

    // TODO: take into account the query String

    protected boolean at(String uri)
    {
        //TODO: remove at some point (Chrome hack).
        AtlassianWebDriver.waitFor(CHROME_HACK_SLEEP);

        String currentUrl = driver.getCurrentUrl();
        String updatedCurrentUrl = currentUrl.replace("!default", "");
        String urlToCheck = baseUrl + uri;

        return currentUrl.startsWith(urlToCheck) || updatedCurrentUrl.startsWith(urlToCheck);
    }

    protected void goTo(String uri)
    {
        if (queryString.size() <= 0)
        {
            driver.get(baseUrl + uri);
        }
        else
        {
            driver.get(baseUrl + uri + "?" + queryString.toString());
        }
    }

    public WebDriver driver()
    {
        return driver;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public String getPageSource()
    {
        return driver.getPageSource();
    }

    abstract public boolean isLoggedIn();

    abstract public boolean isLoggedInAsUser(User user);

    abstract public boolean isAdmin();


}
