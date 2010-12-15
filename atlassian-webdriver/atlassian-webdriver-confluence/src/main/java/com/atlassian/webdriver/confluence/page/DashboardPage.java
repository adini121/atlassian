package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.pageobjects.page.HomePage;

/**
 * Page object implementation for the Dashbaord page in Confluence.
 * TODO: extend to handle more the page properly.
 */
public class DashboardPage extends ConfluenceAbstractPage<DashboardPage> implements HomePage<ConfluenceTestedProduct, DashboardPage>
{
    private static String URI = "/dashboard.action";

    public DashboardPage(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }
}
