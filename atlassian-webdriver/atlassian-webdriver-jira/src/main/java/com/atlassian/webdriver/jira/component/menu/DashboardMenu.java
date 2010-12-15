package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.Init;
import com.atlassian.webdriver.pageobjects.menu.AuiDropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.DropdownMenu;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class DashboardMenu implements DropdownMenu<DashboardMenu>
{
    @Inject
    PageNavigator pageNavigator;

    private AuiDropdownMenu dashboardMenu;

    @Init
    public void initialise()
    {
        dashboardMenu = pageNavigator.build(AuiDropdownMenu.class, ByJquery.$("#home_link").parent("li"));
    }

    public DashboardMenu open()
    {
        dashboardMenu.open();
        return this;
    }

    public boolean isOpen()
    {
        return dashboardMenu.isOpen();
    }

    public DashboardMenu close()
    {
        dashboardMenu.close();
        return this;
    }

}