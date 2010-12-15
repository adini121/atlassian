package com.atlassian.webdriver.greenhopper.component.link;

import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperEnabledProjectsPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperGeneralConfigurationPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperLicenseDetailsPage;
import com.atlassian.webdriver.pageobjects.ClickableLink;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import org.openqa.selenium.By;

/**
 * 
 */
public class GreenHopperAdminSideMenuLinks
{
    @ClickableLink(id = "greenhopper-admin-system", nextPage = GreenHopperEnabledProjectsPage.class)
    WebDriverLink enabledProductsLink;

    @ClickableLink(id="greenhopper-admin-template", nextPage = GreenHopperEnabledProjectsPage.class)
    WebDriverLink projectTemplatesLink;

    @ClickableLink(id="greenhopper-admin-general", nextPage = GreenHopperGeneralConfigurationPage.class)
    WebDriverLink generalConfigurationLink;

    @ClickableLink(id="greenhopper-license", nextPage = GreenHopperLicenseDetailsPage.class)
    WebDriverLink licenseDetailsLink;

    public WebDriverLink getEnabledProductsLink()
    {
        return enabledProductsLink;
    }

    public WebDriverLink getProjectTemplatesLink()
    {
        return projectTemplatesLink;
    }

    public WebDriverLink getGeneralConfigurationLink()
    {
        return generalConfigurationLink;
    }

    public WebDriverLink getLicenseDetailsLink()
    {
        return licenseDetailsLink;
    }
}
