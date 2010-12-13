package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.component.menu.DropdownMenu;
import com.atlassian.webdriver.component.menu.UserMenu;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.LogoutPage;
import org.openqa.selenium.By;

/**
 * Object for interacting with the User Menu in the JIRA Header.
 */
public class JiraUserMenu extends AbstractComponent<JiraTestedProduct, JiraUserMenu> implements DropdownMenu, UserMenu
{
    private final Link<LogoutPage> LOG_OUT = new Link(By.id("log_out"), LogoutPage.class);

    private AuiDropdownMenu<JiraTestedProduct> userMenu;

    public JiraUserMenu(JiraTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(By.id("header-details-user"));
        userMenu = getTestedProduct().getComponent(getComponentLocator(), AuiDropdownMenu.class);
    }

    public LogoutPage logout()
    {
        return activate(LOG_OUT);
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