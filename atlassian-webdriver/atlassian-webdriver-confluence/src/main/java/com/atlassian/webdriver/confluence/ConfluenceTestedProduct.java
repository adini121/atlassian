package com.atlassian.webdriver.confluence;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.product.Defaults;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.confluence.page.ConfluenceAbstractPage;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.ConfluenceLoginPage;
import com.atlassian.webdriver.product.AbstractTestedProduct;
import com.atlassian.webdriver.product.ProductInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 */
@Defaults(instanceId = "confluence", contextPath = "/confluence", httpPort = 1990)
public class ConfluenceTestedProduct extends AbstractTestedProduct<DashboardPage, ConfluenceAdminHomePage, ConfluenceLoginPage>
{
    private User loggedInUser;

    public ConfluenceTestedProduct(WebDriver webDriver, ProductInstance productInstance)
    {
        super(webDriver, productInstance);
    }

    public DashboardPage gotoHomePage()
    {
        return gotoPage(DashboardPage.class);
    }

    public ConfluenceAdminHomePage gotoAdminHomePage()
    {
        return gotoPage(ConfluenceAdminHomePage.class);
    }

    public ConfluenceLoginPage gotoLoginPage()
    {
        return gotoPage(ConfluenceLoginPage.class);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
