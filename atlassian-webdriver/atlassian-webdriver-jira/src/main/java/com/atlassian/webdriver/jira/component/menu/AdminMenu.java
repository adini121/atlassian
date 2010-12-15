package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.Init;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.PluginsPage;
import com.atlassian.webdriver.jira.page.ProjectsViewPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import com.atlassian.webdriver.pageobjects.ClickableLink;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.menu.AuiDropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.DropdownMenu;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.inject.Inject;

/**
 * Object for interacting with the Admin menu in the JIRA header.
 * TODO: extend for all available links.
 */
public class AdminMenu implements DropdownMenu<AdminMenu>
{

    private static final By ADMIN_MENU_LOCATOR = ByJquery.$("#admin_link").parent("li");

    @Inject
    PageNavigator pageNavigator;

    @FindBy(id = "plugins_lnk")
    private WebElement pluginsPageLink;

    @ClickableLink(id = "plugins_lnk", nextPage = PluginsPage.class)
    WebDriverLink<PluginsPage> pluginsLink;

    @ClickableLink(id = "license_details_lnk", nextPage = LicenseDetailsPage.class)
    WebDriverLink<LicenseDetailsPage> licenseDetailsLink;

    @ClickableLink(id = "user_browser_lnk", nextPage = UserBrowserPage.class)
    WebDriverLink<UserBrowserPage> userBrowserLink;

    @ClickableLink(id = "view_projects_lnk", nextPage = ProjectsViewPage.class)
    WebDriverLink<ProjectsViewPage> projectsViewPage;

    private AuiDropdownMenu adminMenu;

    @Init
    public void initialise()
    {
        adminMenu = pageNavigator.build(AuiDropdownMenu.class, ADMIN_MENU_LOCATOR);
    }

    public PluginsPage gotoPluginsPage()
    {
        return pluginsLink.activate();
    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        return licenseDetailsLink.activate();
    }

    public UserBrowserPage gotoUserBrowserPage()
    {
        return userBrowserLink.activate();
    }

    public ProjectsViewPage gotoProjectsPage()
    {
        return projectsViewPage.activate();
    }

    public AdminMenu open()
    {
        adminMenu.open();
        return this;
    }

    public boolean isOpen()
    {
        return adminMenu.isOpen();
    }

    public AdminMenu close()
    {
        adminMenu.close();
        return this;
    }
}