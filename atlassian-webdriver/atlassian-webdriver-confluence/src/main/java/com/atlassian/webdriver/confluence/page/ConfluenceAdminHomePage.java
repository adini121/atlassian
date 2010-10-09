package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.page.AdminHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ConfluenceAdminHomePage extends ConfluenceAbstractPage<ConfluenceAdminHomePage> implements AdminHomePage<ConfluenceTestedProduct, ConfluenceAdminHomePage>
{
    private static final String URI = "/admin/console.action";

    @FindBy (linkText = "Plugins")
    private WebElement pluginsLink;

    @FindBy (linkText = "License Details")
    private WebElement licenseDetailsLink;

    public ConfluenceAdminHomePage(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }

    public PluginsPage gotoPluginsPage()
    {
        pluginsLink.click();

        return new PluginsPage(getTestedProduct()).get(true);
    }

    public LicenseDetailsPage gotoLicenseDetailsPage()
    {
        licenseDetailsLink.click();

        return new LicenseDetailsPage(getTestedProduct()).get(true);
    }
}
