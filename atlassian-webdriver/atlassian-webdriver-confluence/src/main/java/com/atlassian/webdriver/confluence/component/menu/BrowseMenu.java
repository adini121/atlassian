package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.pageobjects.menu.AjsDropdownMenu;
import com.atlassian.webdriver.pageobjects.menu.DropdownMenu;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class BrowseMenu  extends AbstractComponent<ConfluenceTestedProduct, BrowseMenu>
        implements DropdownMenu
{
    private final static By BROWSER_MENU_LOCATOR = ByJquery.$("#browse-menu-link").parent("li");

    private final static Link<ConfluenceAdminHomePage> ADMIN_PAGE_LINK = new Link(By.id("administration-link"), ConfluenceAdminHomePage.class);
    private final static Link<PeopleDirectoryPage> PEOPLE_DIRECTORY_LINK = new Link(By.id("people-directory-link"), PeopleDirectoryPage.class);

    private AjsDropdownMenu<ConfluenceTestedProduct> browseMenu;

    public BrowseMenu(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(BROWSER_MENU_LOCATOR);
        browseMenu = getTestedProduct().getComponent(getComponentLocator(), AjsDropdownMenu.class);
    }

    public ConfluenceAdminHomePage gotoAdminPage()
    {
        return activate(ADMIN_PAGE_LINK);
    }

    public PeopleDirectoryPage gotoPeopleDirectoryPage()
    {
        return activate(PEOPLE_DIRECTORY_LINK);
    }

    public <T extends PageObject> T activate(final Link<T> link)
    {
        return browseMenu.activate(link);
    }

    public void open()
    {
        browseMenu.open();
    }

    public boolean isOpen()
    {
        return browseMenu.isOpen();
    }

    public void close()
    {
        browseMenu.close();
    }
}
