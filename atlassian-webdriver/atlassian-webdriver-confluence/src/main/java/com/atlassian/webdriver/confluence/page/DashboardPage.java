package com.atlassian.webdriver.confluence.page;


/**
 * Page object implementation for the Dashbaord page in Confluence.
 * TODO: extend to handle more the page properly.
 */
public class DashboardPage extends ConfluenceAbstractPage
{
    private static String URI = "/dashboard.action";

    public String getUrl()
    {
        return URI;
    }
}
