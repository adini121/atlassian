package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.UserMenu;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.UserDiscoverable;
import org.openqa.selenium.By;

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
        return getDriver().elementExists(By.id("administration-link"));
    }

    public boolean isLoggedIn()
    {
        return getDriver().elementExists(By.id("user-menu-link"));
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

    public P get(boolean activated)
    {
        super.get(uri, activated);
        return (P) this;
    }

    @Override
    public void doWait()
    {
        getDriver().waitUntilElementIsLocated(By.id("footer"));
    }

    @Override
    public void doCheck(final String uri, final boolean activated)
    {

        // Check for WebSudo
        if (uri != null && !uri.equals(AdministratorAccessPage.URI) && at(AdministratorAccessPage.URI))
        {
            getTestedProduct().gotoPage(AdministratorAccessPage.class, true).login(getTestedProduct().getLoggedInUser());
        }

        super.doCheck(uri, activated);
    }
}
