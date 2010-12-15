package com.atlassian.webdriver.confluence.page;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.WaitUntil;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.header.Header;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.header.ConfluenceHeader;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.ConfluenceUserMenu;
import com.atlassian.webdriver.pageobjects.page.AbstractPage;
import com.atlassian.webdriver.pageobjects.page.UserDiscoverable;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class ConfluenceAbstractPage implements UserDiscoverable, Page
{
    @Inject
    AtlassianWebDriver driver;
    
    @Inject
    PageNavigator pageNavigator;

    @Inject
    ConfluenceTestedProduct testedProduct;

    public boolean isAdmin()
    {
        return getHeader().isAdmin();
    }

    public boolean isLoggedIn()
    {
        return getHeader().isLoggedIn();
    }

    public boolean isLoggedInAsUser(final User user)
    {
        return getHeader().isLoggedInAsUser(user);
    }

    public BrowseMenu getBrowseMenu()
    {
        return getHeader().getBrowseMenu();
    }

    public ConfluenceUserMenu getUserMenu()
    {
        return getHeader().getUserMenu();
    }

    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(By.id("footer"));
    }

    @Override
    public void doCheck(final String uri, final boolean activated)
    {

        // Check for WebSudo
        if (uri != null && !uri.equals(AdministratorAccessPage.URI) && at(AdministratorAccessPage.URI))
        {
            pageNavigator.build(AdministratorAccessPage.class).login(testedProduct.getLoggedInUser());
        }
    }

    private boolean at(String uri)
    {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    public ConfluenceHeader getHeader()
    {
        return getTestedProduct().getComponent(ConfluenceHeader.class);
    }
}
