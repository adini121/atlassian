package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.pageobjects.menu.AjsDropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.DropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.UserMenu;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.LogoutPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ConfluenceUserMenu extends AbstractComponent<ConfluenceTestedProduct, ConfluenceUserMenu> implements DropdownMenu, UserMenu
{
    private final static By USER_MENU_LOCATOR = ByJquery.$("#user-menu-link").parent("li");

    private final static Link<LogoutPage> LOGOUT_LINK = new Link(By.id("logout-link"), LogoutPage.class);

    private AjsDropdownMenu<ConfluenceTestedProduct> userMenu;

    public ConfluenceUserMenu(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(USER_MENU_LOCATOR);
        userMenu = getTestedProduct().getComponent(getComponentLocator(), AjsDropdownMenu.class);
    }

    public LogoutPage logout()
    {
        return activate(LOGOUT_LINK);
    }

    public <T extends PageObject> T activate(final Link<T> link)
    {
        return userMenu.activate(link);
    }

    public void open()
    {
        userMenu.open();
    }

    public boolean isOpen()
    {
        return userMenu.isOpen();
    }

    public void close()
    {
        userMenu.close();
    }
}
