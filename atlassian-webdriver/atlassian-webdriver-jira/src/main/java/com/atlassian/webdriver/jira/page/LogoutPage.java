package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object implementation for the Logout page in JIRA. 
 */
public class LogoutPage extends JiraAbstractPage<LogoutPage>
{

    private static final String URI = "/secure/Logout.jspa";

    public LogoutPage(JiraTestedProduct jiraTestedProduct)
    {
        super(jiraTestedProduct, URI);
    }

    public LogoutPage confirmLogout()
    {
        if (getDriver().elementExists(By.id("confirm-logout-submit")))
        {
            getDriver().findElement(By.id("confirm-logout-submit")).click();
            return testedProduct.gotoPage(LogoutPage.class, true);
        }
        else
        {
            throw new IllegalStateException("Already logged out. Not at the confirm logout page.");
        }
    }

    @Override
    public void doWait()
    {
        getDriver().waitUntilElementIsLocated(ByJquery.$("h2:contains(Logout)"));
    }
}