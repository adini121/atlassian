package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.ClickableLink;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.components.ajs.AjsDropdownMenu;
import com.atlassian.webdriver.pageobjects.components.DropdownMenu;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class BrowseMenu implements DropdownMenu<BrowseMenu>
{
    @Inject
    AtlassianWebDriver driver;

    @Inject
    PageBinder pageBinder;

    @ClickableLink(id = "administration-link", nextPage = ConfluenceAdminHomePage.class)
    WebDriverLink<ConfluenceAdminHomePage> adminPageLink;

    @ClickableLink(id = "people-directory-link", nextPage = PeopleDirectoryPage.class)
    WebDriverLink<PeopleDirectoryPage> peopleDirectoryLink;

    private AjsDropdownMenu browseMenu;

    @Init
    public void initialise()
    {
        browseMenu = pageBinder.bind(AjsDropdownMenu.class, ByJquery.$("#browse-menu-link").parent("li"));
    }

    public ConfluenceAdminHomePage gotoAdminPage()
    {
        return adminPageLink.activate();
    }

    public PeopleDirectoryPage gotoPeopleDirectoryPage()
    {
        return peopleDirectoryLink.activate();
    }

    public <T extends Page> T activate(final WebDriverLink<T> link)
    {
        return browseMenu.activate(link);
    }

    public BrowseMenu open()
    {
        browseMenu.open();
        return this;
    }

    public boolean isOpen()
    {
        return browseMenu.isOpen();
    }

    public BrowseMenu close()
    {
        browseMenu.close();
        return this;
    }
}
