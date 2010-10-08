package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.UserMenu;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.UserDiscoverable;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class ConfluenceAbstractPage<P extends PageObject> extends AbstractPage<ConfluenceTestedProduct, P>
    implements UserDiscoverable
{
    private final String uri;

    public ConfluenceAbstractPage(ConfluenceTestedProduct testedProduct, String uri)
    {
        super(testedProduct);
        this.uri = uri;
    }



    public boolean isAdmin()
    {
        return Check.elementExists(By.id("administration-link"));
    }

    public boolean isLoggedIn()
    {
        return Check.elementExists(By.id("user-menu-link"));
    }

    public boolean isLoggedInAsUser(final User user)
    {
        if (isLoggedIn())
        {
            return getDriver().findElement(By.id("user-menu-link"))
                    .getText()
                    .equals(user.getFullName());
        }

        return false;
    }

    public BrowseMenu getBrowseMenu()
    {
        if (isLoggedIn())
        {
            return new BrowseMenu(getTestedProduct());
        }
        else
        {
            throw new RuntimeException("Tried to get the browse menu but the user is not logged in.");
        }
    }

    public UserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return new UserMenu(getTestedProduct());
        }
        else
        {
            throw new RuntimeException("Tried to get the user menu but the user is not logged in.");
        }
    }

    /**
     * Must override the AbstractPage version as need to handle the Administrator Access
     * page. (WebSudo)
     * @param activated
     */
    public P get(boolean activated)
    {
        if (!activated && !at(uri))
        {
            goTo(uri);
        }

        //Check whether we are on the admin access page and it wasn't requested.
        //If this is the case log in the user automatically.
        if (!uri.equals(AdministratorAccessPage.URI) && at(AdministratorAccessPage.URI))
        {
            new AdministratorAccessPage(getTestedProduct()).get(true).login(getTestedProduct().getLoggedInUser());
        }


        if (activated && !at(uri))
        {
            throw new IllegalStateException("Expected to be at uri: " + (getBaseUrl() + uri) + ", instead at: " + getDriver().getCurrentUrl());
        }

        return (P) this;

    }

}
