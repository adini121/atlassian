package com.atlassian.webdriver.greenhopper.component.link;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperEnabledProjectsPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperGeneralConfigurationPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperLicenseDetailsPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperProjectTemplatesPage;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import org.openqa.selenium.By;

/**
 * 
 */
public class GreenHopperAdminSideMenuLinks
{
    public static final WebDriverLink<GreenHopperEnabledProjectsPage> ENABLED_PROJECTS =
            new Link<GreenHopperEnabledProjectsPage>(By.id("greenhopper-admin-system"), GreenHopperEnabledProjectsPage.class);

    public static final Link<GreenHopperProjectTemplatesPage> PROJECT_TEMPLATES =
            new Link<GreenHopperProjectTemplatesPage>(By.id("greenhopper-admin-template"), GreenHopperProjectTemplatesPage.class);

    public static final Link<GreenHopperGeneralConfigurationPage> GENERAL_CONFIGURATION =
            new Link<GreenHopperGeneralConfigurationPage>(By.id("greenhopper-admin-general"), GreenHopperGeneralConfigurationPage.class);

    public static final Link<GreenHopperLicenseDetailsPage> LICENSE_DETAILS =
            new Link<GreenHopperLicenseDetailsPage>(By.id("greenhopper-license"), GreenHopperLicenseDetailsPage.class);

    
    private GreenHopperAdminSideMenuLinks()
    {
        throw new UnsupportedOperationException("Cannot instantiate GreenHopperAdminMenuLinks");
    }
}
