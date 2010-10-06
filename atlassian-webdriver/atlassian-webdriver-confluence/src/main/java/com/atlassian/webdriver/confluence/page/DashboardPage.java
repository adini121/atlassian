package com.atlassian.webdriver.confluence.page;

import org.openqa.selenium.WebDriver;

/**
 * Page object implementation for the Dashbaord page in Confluence.
 * TODO: extend to handle more the page properly.
 */
public class DashboardPage extends ConfluenceAbstractPage
{
    private static String URI = "/dashboard.action";

    public DashboardPage(WebDriver driver)
    {
        super(driver);
    }

    public DashboardPage get(final boolean activated)
    {
        get(URI, activated);

        return this;
    }
}
