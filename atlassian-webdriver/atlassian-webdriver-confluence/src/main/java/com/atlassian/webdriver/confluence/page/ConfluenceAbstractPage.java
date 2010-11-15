package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.header.Header;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.header.ConfluenceHeader;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.ConfluenceUserMenu;
import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.UserDiscoverable;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class ConfluenceAbstractPage<P extends PageObject> extends AbstractPage<ConfluenceTestedProduct, P>
    implements UserDiscoverable, Header<ConfluenceHeader>
{
    private final String uri;

    public ConfluenceAbstractPage(ConfluenceTestedProduct testedProduct, String uri)
    {
        super(testedProduct);
        this.uri = uri;
    }

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

    public ConfluenceHeader getHeader()
    {
        return getTestedProduct().getComponent(ConfluenceHeader.class);
    }
}
