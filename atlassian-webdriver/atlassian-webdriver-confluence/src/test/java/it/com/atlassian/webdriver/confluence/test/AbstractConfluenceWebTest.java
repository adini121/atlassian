package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import org.junit.After;
import org.junit.Before;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public abstract class AbstractConfluenceWebTest
{

    protected static final ConfluenceTestedProduct CONFLUENCE = TestedProductFactory.create(ConfluenceTestedProduct.class);

    protected DashboardPage dashboard;

    protected PageNavigator pageNavigator;

    @Before
    public void login()
    {
        dashboard = CONFLUENCE.gotoLoginPage().loginAsSysAdmin(DashboardPage.class);
        pageNavigator = CONFLUENCE.getPageNavigator();
    }

    @After
    public void cleanUpCookies()
    {
        CONFLUENCE.getTester().getDriver().manage().deleteAllCookies();
    }

}
