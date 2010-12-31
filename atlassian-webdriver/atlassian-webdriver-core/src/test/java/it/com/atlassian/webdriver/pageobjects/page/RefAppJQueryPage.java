package it.com.atlassian.webdriver.pageobjects.page;

import com.atlassian.webdriver.utils.by.ByJquery;
import it.com.atlassian.webdriver.pageobjects.components.AdminDropDownMenu;

/**
 * Represents the page returned by the JQueryServlet plugin that is deployed to RefApp
 */
public class RefAppJQueryPage extends RefappAbstractPage
{
    public String getUrl() {
        return "/plugins/servlet/webdriver/jquerypage";
    }

    public AdminDropDownMenu adminMenu()
    {
        return pageBinder.bind(AdminDropDownMenu.class);
    }

    public AdminDropDownMenu openAdminMenu() {
        return pageBinder.bind(AdminDropDownMenu.class).open();
    }
}
