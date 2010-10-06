package com.atlassian.webdriver.jira.page;


import com.atlassian.webdriver.jira.component.dashboard.Gadget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object implementation for the Dashbaord page in JIRA.
 * TODO: extend to handle more operations like addGadget.
 */
public class DashboardPage extends JiraAbstractPage
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