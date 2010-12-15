package com.atlassian.webdriver.confluence.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ConfluenceAdminHomePage extends ConfluenceAbstractPage
{
    private static final String URI = "/admin/console.action";

    @FindBy (linkText = "Plugins")
    private WebElement pluginsLink;

    @FindBy (linkText = "License Details")
    private WebElement licenseDetailsLink;

    public String getUrl()
    {
        return URI;
    }

    public PluginsPage gotoPluginsPage()
    {
        pluginsLink.click();

        return pageNavigator.build(PluginsPage.class);
    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        licenseDetailsLink.click();

        return pageNavigator.build(LicenseDetailsPage.class);
    }
}
