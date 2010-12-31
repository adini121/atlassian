package com.atlassian.webdriver.confluence.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.ValidateState;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.header.ConfluenceHeader;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.ConfluenceUserMenu;
import com.atlassian.webdriver.confluence.component.UserDiscoverable;
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
    PageBinder pageBinder;

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

    @ValidateState
    public void doCheck()
    {

        String uri = getUrl();

        // Check for WebSudo
        if (uri != null && !uri.equals(AdministratorAccessPage.URI) && at(AdministratorAccessPage.URI))
        {
            pageBinder.bind(AdministratorAccessPage.class).login(testedProduct.getLoggedInPassword());
        }
    }

    protected boolean at(String uri)
    {
        String currentUrl = driver.getCurrentUrl();
        String updatedCurrentUrl = currentUrl.replace("!default", "");
        String urlToCheck = testedProduct.getProductInstance().getBaseUrl() + uri;

        return currentUrl.startsWith(urlToCheck) || updatedCurrentUrl.startsWith(urlToCheck);
    }

    public ConfluenceHeader getHeader()
    {
        return pageBinder.bind(ConfluenceHeader.class);
    }
}
