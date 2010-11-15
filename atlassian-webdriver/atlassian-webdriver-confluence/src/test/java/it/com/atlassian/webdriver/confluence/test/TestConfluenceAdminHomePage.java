package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webdriver.browsers.WebDriverBrowserAutoInstall;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestConfluenceAdminHomePage extends AbstractConfluenceAutoBrowserWebTest
{

    @Test
    public void testAdminHomePage()
    {
        dashboard.gotoPage(ConfluenceAdminHomePage.class);
    }

}
