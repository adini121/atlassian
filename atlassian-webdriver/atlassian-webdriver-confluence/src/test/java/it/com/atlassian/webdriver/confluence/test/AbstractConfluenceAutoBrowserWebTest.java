package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.product.TestedProductFactory;
import org.junit.After;
import org.junit.Before;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public abstract class AbstractConfluenceAutoBrowserWebTest
{

    private static final ConfluenceTestedProduct CONFLUENCE = TestedProductFactory.create(ConfluenceTestedProduct.class, "confluence",
            WebDriverBrowserAutoInstall.INSTANCE.getDriver());

    protected DashboardPage dashboard;

    @Before
    public void login()
    {
        dashboard = CONFLUENCE.gotoLoginPage().loginAsAdmin();
    }

    @After
    public void cleanUpCookies()
    {
        CONFLUENCE.getDriver().manage().deleteAllCookies();
    }

}
