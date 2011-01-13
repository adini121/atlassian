package it.com.atlassian.webdriver.pageobjects.page;

import it.com.atlassian.webdriver.pageobjects.components.AdminMenu;

/**
 * Represents the page returned by the JQueryServlet plugin that is deployed to RefApp
 */
public class RefAppJQueryPage extends RefappAbstractPage
{
    public String getUrl() {
        return "/plugins/servlet/webdriver/jquerypage";
    }

    public AdminMenu adminMenu()
    {
        return pageBinder.bind(AdminMenu.class);
    }

    public AdminMenu openAdminMenu() {
        return pageBinder.bind(AdminMenu.class).open();
    }
}
