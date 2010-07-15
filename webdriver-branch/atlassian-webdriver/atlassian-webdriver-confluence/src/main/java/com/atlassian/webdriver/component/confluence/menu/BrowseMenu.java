package com.atlassian.webdriver.component.confluence.menu;

import com.atlassian.webdriver.component.menu.AjsDropdownMenu;
import com.atlassian.webdriver.page.confluence.AdministrationPage;
import com.atlassian.webdriver.page.confluence.ConfluencePage;
import com.atlassian.webdriver.page.confluence.PeopleDirectoryPage;
import com.atlassian.webdriver.utils.ByJquery;
import com.atlassian.webdriver.utils.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class BrowseMenu extends AjsDropdownMenu
{

    public BrowseMenu(WebDriver driver)
    {
        super(ByJquery.$("('#browse-menu-link').parent('li')"),driver);
    }

    public AdministrationPage gotoAdminPage()
    {
        activate("administration-link");

        return ConfluencePage.ADMINPAGE.get(getDriver(), true);
    }

    public PeopleDirectoryPage gotoPeopleDirectoryPage()
    {
        activate("people-directory-link");

        return ConfluencePage.PEOPLE_DIRECTORY_PAGE.get(getDriver(), true);
    }

}
