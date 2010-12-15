package com.atlassian.webdriver.jira.page;

import com.atlassian.pageobjects.navigator.WaitUntil;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

/**
 * Page object implementation for the Logout page in JIRA. 
 */
public class LogoutPage extends JiraAbstractPage
{

    private static final String URI = "/secure/Logout.jspa";

    public String getUrl()
    {
        return URI;
    }

    public LogoutPage confirmLogout()
    {
        if (driver.elementExists(By.id("confirm-logout-submit")))
        {
            driver.findElement(By.id("confirm-logout-submit")).click();
            return pageNavigator.build(LogoutPage.class);
        }
        else
        {
            throw new IllegalStateException("Already logged out. Not at the confirm logout page.");
        }
    }

    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(ByJquery.$("h2:contains(Logout)"));
    }
}