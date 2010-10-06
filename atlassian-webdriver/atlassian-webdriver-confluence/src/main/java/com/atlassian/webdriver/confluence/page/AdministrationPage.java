package com.atlassian.webdriver.confluence.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AdministrationPage extends ConfluenceAbstractPage
{
    private static final String URI = "/admin/console.action";

    @FindBy (linkText = "Plugins")
    private WebElement pluginsLink;

    @FindBy (linkText = "License Details")
    private WebElement licenseDetailsLink;

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

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        licenseDetailsLink.click();

        return ConfluencePage.LICENSE_DETAILS_PAGE.get(driver, true);
    }
}
