package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.ClickableLink;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.components.ajs.AjsDropdownMenu;
import com.atlassian.webdriver.pageobjects.components.DropdownMenu;
import com.atlassian.webdriver.pageobjects.components.UserMenu;
import com.atlassian.webdriver.confluence.page.LogoutPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ConfluenceUserMenu implements DropdownMenu<ConfluenceUserMenu>, UserMenu
{
    private final By USER_MENU_LOCATOR = ByJquery.$("#user-menu-link").parent("li");

    @Inject
    AtlassianWebDriver driver;

    @Inject
    PageBinder pageBinder;

    @ClickableLink(id = "logout-link", nextPage = LogoutPage.class)
    WebDriverLink<LogoutPage> logoutLink;


    private AjsDropdownMenu userMenu;

    @Init
    public void initialise()
    {
        userMenu = pageBinder.bind(AjsDropdownMenu.class, USER_MENU_LOCATOR);
    }

    public LogoutPage logout()
    {
        return logoutLink.activate();
    }

    public <T extends Page> T activate(final WebDriverLink<T> link)
    {
        return userMenu.activate(link);
    }

    public ConfluenceUserMenu open()
    {
        userMenu.open();
        return this;
    }

    public boolean isOpen()
    {
        return userMenu.isOpen();
    }

    public ConfluenceUserMenu close()
    {
        userMenu.close();
        return this;
    }
}
