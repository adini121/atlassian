package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.Init;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.ClickableLink;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.menu.AjsDropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.DropdownMenu;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

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
    PageNavigator pageNavigator;

    @ClickableLink(id = "administration-link", nextPage = ConfluenceAdminHomePage.class)
    WebDriverLink<ConfluenceAdminHomePage> adminPageLink;

    @ClickableLink(id = "people-directory-link", nextPage = PeopleDirectoryPage.class)
    WebDriverLink<PeopleDirectoryPage> peopleDirectoryLink;

    private AjsDropdownMenu browseMenu;

    @Init
    public void initialise()
    {
        browseMenu = pageNavigator.build(AjsDropdownMenu.class, ByJquery.$("#browse-menu-link").parent("li"));
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
