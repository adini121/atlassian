package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.webdriver.component.menu.AjsDropdownMenu;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import com.atlassian.webdriver.utils.by.ByJquery;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class BrowseMenu extends AjsDropdownMenu<ConfluenceTestedProduct>
{

    public BrowseMenu(ConfluenceTestedProduct testedProduct)
    {
        super(ByJquery.$("#browse-menu-link").parent("li"), testedProduct);
    }

    public ConfluenceAdminHomePage gotoAdminPage()
    {
        activate("administration-link");

        return getTestedProduct().gotoPage(ConfluenceAdminHomePage.class, true);
    }

    public PeopleDirectoryPage gotoPeopleDirectoryPage()
    {
        activate("people-directory-link");

        return getTestedProduct().gotoPage(PeopleDirectoryPage.class, true);
    }

}
