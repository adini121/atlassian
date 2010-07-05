package com.atlassian.webdriver.page.jira;


import com.atlassian.webdriver.component.jira.dashboard.Gadget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Simple implementation for a DashboardPage view TODO: extend to handle more operations like addGadget.
 */
public class DashboardPage extends JiraWebDriverPage
{
    private static final String URI = "/secure/Dashboard.jspa";

    public DashboardPage(WebDriver driver)
    {
        super(driver);
    }

    public DashboardPage get(boolean activated)
    {
        get(URI, activated);
        waitUntilLocated(By.className("layout"));

        return this;
    }

    /**
     * TODO: fix this.
     */
    public boolean canAddGadget()
    {
        return false;
    }

    public Gadget getGadget(String gadgetId)
    {
        return new Gadget(gadgetId, driver);
    }


}