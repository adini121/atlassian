package com.atlassian.webdriver.component.confluence.menu;

import com.atlassian.webdriver.component.confluence.sherpa.WelcomeScreen;
import com.atlassian.webdriver.component.menu.AjsDropdownMenu;
import com.atlassian.webdriver.page.confluence.ConfluencePage;
import com.atlassian.webdriver.page.confluence.LogoutPage;
import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class UserMenu extends AjsDropdownMenu
{

    public UserMenu(WebDriver driver)
    {
        super(ByJquery.$("('#user-menu-link').parent('li')"), driver);
    }

    public LogoutPage logout()
    {
        activate("logout-link");

        return ConfluencePage.LOGOUTPAGE.get(getDriver(), true);
    }

    public WelcomeScreen showWelcomeScreen()
    {
        activate("show-sherpa-lightbox");

        return new WelcomeScreen(getDriver());
    }

}
