package com.atlassian.webdriver.confluence.component.menu;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.sherpa.WelcomeScreen;
import com.atlassian.webdriver.component.menu.AjsDropdownMenu;
import com.atlassian.webdriver.confluence.page.ConfluencePage;
import com.atlassian.webdriver.confluence.page.LogoutPage;
import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class UserMenu extends AjsDropdownMenu<ConfluenceTestedProduct>
{

    public UserMenu(ConfluenceTestedProduct testedProduct)
    {
        super(ByJquery.$("('#user-menu-link').parent('li')"), testedProduct);
    }

    public LogoutPage logout()
    {
        activate("logout-link");

        return new LogoutPage(getTestedProduct()).get(true);
    }

    public WelcomeScreen showWelcomeScreen()
    {
        activate("show-sherpa-lightbox");

        return new WelcomeScreen(getDriver());
    }

}
