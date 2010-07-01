package com.atlassian.webdriver.page.jira;


import com.atlassian.webdriver.component.jira.dashboard.Gadget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Simple implementation for a DashboardPage view TODO: extend to handle more operations like addGadget.
 */
public class DashboardPage extends JiraWebDriverPage
{

    public DashboardPage(WebDriver driver)
    {
        super(driver);
    }

    public DashboardPage get()
    {

        if (!at("/secure/Dashboard.jspa"))
        {
            goTo("/secure/Dashboard.jspa");
        }

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