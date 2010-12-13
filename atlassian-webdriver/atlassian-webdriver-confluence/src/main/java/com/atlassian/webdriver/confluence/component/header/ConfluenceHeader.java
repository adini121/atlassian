package com.atlassian.webdriver.confluence.component.header;

import com.atlassian.webdriver.component.header.AbstractHeader;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.menu.BrowseMenu;
import com.atlassian.webdriver.confluence.component.menu.ConfluenceUserMenu;
import com.atlassian.webdriver.page.UserDiscoverable;
import com.atlassian.webdriver.utils.user.User;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class ConfluenceHeader extends AbstractHeader<ConfluenceTestedProduct, ConfluenceHeader> implements UserDiscoverable
{

    private final By CONFLUENCE_HEADER_LOCATION = By.id("header");
    private final By USER_MENU_LOCATOR = By.id("user-menu-link");
    private final By ADMIN_MENU_LOCATOR = By.id("administration-link");

    public ConfluenceHeader(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(CONFLUENCE_HEADER_LOCATION);
    }

    public boolean isLoggedIn()
    {
        return getDriver().elementExistsAt(USER_MENU_LOCATOR, getHeaderElement());
    }

    public boolean isLoggedInAsUser(final User user)
    {
        if (isLoggedIn())
        {
            return getHeaderElement().findElement(USER_MENU_LOCATOR)
                    .getText()
                    .equals(user.getFullName());
        }

        return false;
    }

    public boolean isAdmin()
    {
        return getDriver().elementExistsAt(ADMIN_MENU_LOCATOR, getHeaderElement());
    }

    public ConfluenceUserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return getTestedProduct().getComponent(ConfluenceUserMenu.class);
        }
        else
        {
            throw new RuntimeException("Tried to get the user menu but the user is not logged in.");
        }
    }

    public BrowseMenu getBrowseMenu()
    {
        return getTestedProduct().getComponent(BrowseMenu.class);
    }
}
