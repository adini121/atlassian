package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.component.menu.DropdownMenu;
import com.atlassian.webdriver.component.menu.Menu;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class DashboardMenu extends AbstractComponent<JiraTestedProduct, JiraUserMenu>
        implements DropdownMenu
{
    private static final By DASHBOARD_MENU_LOCATOR = ByJquery.$("#home_link").parent("li");

    private AuiDropdownMenu<JiraTestedProduct> dashboardMenu;

    public DashboardMenu(JiraTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(DASHBOARD_MENU_LOCATOR);
        dashboardMenu = getTestedProduct().getComponent(getComponentLocator(), AuiDropdownMenu.class);
    }

    public <T extends PageObject> T activate(final Link<T> link)
    {
        return dashboardMenu.activate(link);
    }
}