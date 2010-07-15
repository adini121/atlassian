package com.atlassian.webdriver.page.confluence;

import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AdministrationPage extends ConfluenceWebDriverPage
{
    private static final String URI = "/admin/console.action";

    @FindBy (linkText = "Plugins")
    private WebElement pluginsLink;

    public AdministrationPage(WebDriver driver)
    {
        super(driver);
    }

    public AdministrationPage get(final boolean activated)
    {
        get(URI, activated);

        return this;
    }

    public PluginsPage gotoPluginsPage()
    {
        pluginsLink.click();

        return ConfluencePage.PLUGINSPAGE.get(driver, true);
    }
}
