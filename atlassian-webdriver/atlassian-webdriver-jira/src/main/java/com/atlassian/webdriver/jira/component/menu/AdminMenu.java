package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.component.menu.AuiDropdownMenu;
import com.atlassian.webdriver.component.menu.DropdownMenu;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.PluginsPage;
import com.atlassian.webdriver.jira.page.ProjectsViewPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

/**
 * Object for interacting with the Admin menu in the JIRA header.
 * TODO: extend for all available links.
 */
public class AdminMenu extends AbstractComponent<JiraTestedProduct, AdminMenu>
        implements DropdownMenu
{

    private static final By ADMIN_MENU_LOCATOR = ByJquery.$("#admin_link").parent("li");

    private static final Link<PluginsPage> PLUGINS_PAGE = new Link(By.id("plugins_lnk"), PluginsPage.class);
    private static final Link<LicenseDetailsPage> LICENSE_DETAILS_PAGE = new Link(By.id("license_details_lnk"), LicenseDetailsPage.class);
    private static final Link<UserBrowserPage> USER_BROWSER_PAGE = new Link(By.id("user_browser_lnk"), UserBrowserPage.class);
    private static final Link<ProjectsViewPage> PROJECTS_VIEW_PAGE = new Link(By.id("view_projects_lnk"), ProjectsViewPage.class);

    private AuiDropdownMenu<JiraTestedProduct> adminMenu;

    public AdminMenu(JiraTestedProduct jiraTestedProduct)
    {
        super(jiraTestedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(ADMIN_MENU_LOCATOR);
        adminMenu = getTestedProduct().getComponent(getComponentLocator(), AuiDropdownMenu.class);
    }

    public PluginsPage gotoPluginsPage()
    {
        return activate(PLUGINS_PAGE);
    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        return activate(LICENSE_DETAILS_PAGE);
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        return activate(USER_BROWSER_PAGE);
    }

    public ProjectsViewPage gotoProjectsPage()
    {
        return activate(PROJECTS_VIEW_PAGE);
    }

    public <T extends PageObject> T activate(final Link<T> link)
    {
        return adminMenu.activate(link);
    }

    public void open()
    {
        adminMenu.open();
    }

    public boolean isOpen()
    {
        return adminMenu.isOpen();
    }

    public void close()
    {
        adminMenu.close();
    }
}