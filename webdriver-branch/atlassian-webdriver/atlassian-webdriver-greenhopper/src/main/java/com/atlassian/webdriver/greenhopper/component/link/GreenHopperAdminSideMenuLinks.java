package com.atlassian.webdriver.greenhopper.component.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.greenhopper.page.license.GreenHopperLicenseDetailsPage;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class GreenHopperAdminSideMenuLinks
{
    public static Link<GreenHopperLicenseDetailsPage> LICENSE_DETAILS_LINK = new Link(By.id("greenhopper-license"));

    private GreenHopperAdminSideMenuLinks() {}

}
