package com.atlassian.webdriver.greenhopper.component.link;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperEnabledProjectsPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperGeneralConfigurationPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperLicenseDetailsPage;
import com.atlassian.webdriver.greenhopper.page.admin.GreenHopperProjectTemplatesPage;
import org.openqa.selenium.By;

/**
 * 
 */
public class GreenHopperAdminSideMenuLinks
{
    public Link<GreenHopperEnabledProjectsPage> ENABLED_PROJECTS =
            new Link<GreenHopperEnabledProjectsPage>(By.id("greenhopper-admin-system"), GreenHopperEnabledProjectsPage.class);

    public Link<GreenHopperProjectTemplatesPage> PROJECT_TEMPLATES =
            new Link<GreenHopperProjectTemplatesPage>(By.id("greenhopper-admin-template"), GreenHopperProjectTemplatesPage.class);

    public Link<GreenHopperGeneralConfigurationPage> GENERAL_CONFIGURATION =
            new Link<GreenHopperGeneralConfigurationPage>(By.id("greenhopper-admin-general"), GreenHopperGeneralConfigurationPage.class);

    public Link<GreenHopperLicenseDetailsPage> LICENSE_DETAILS =
            new Link<GreenHopperLicenseDetailsPage>(By.id("greenhopper-license"), GreenHopperLicenseDetailsPage.class);

    
    private GreenHopperAdminSideMenuLinks()
    {
        throw new UnsupportedOperationException("Cannot instantiate GreenHopperAdminMenuLinks");
    }
}
