package com.atlassian.webdriver.jira.page;


import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.dashboard.Gadget;
import com.atlassian.webdriver.page.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object implementation for the Dashbaord page in JIRA.
 * TODO: extend to handle more operations like addGadget.
 */
public class DashboardPage extends JiraAbstractPage<DashboardPage> implements HomePage<JiraTestedProduct, DashboardPage>
{
    private static final String URI = "/secure/Dashboard.jspa";

    public DashboardPage(JiraTestedProduct jiraTestedProduct)
    {
        super(jiraTestedProduct, URI);
    }

    @Override
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
        return new Gadget(gadgetId, getTestedProduct());
    }


}