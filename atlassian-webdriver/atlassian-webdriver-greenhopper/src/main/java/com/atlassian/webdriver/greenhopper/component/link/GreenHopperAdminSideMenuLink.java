package com.atlassian.webdriver.greenhopper.component.link;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.greenhopper.page.license.GreenHopperLicenseDetailsPage;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class GreenHopperAdminSideMenuLink extends Link
{
    public GreenHopperAdminSideMenuLink()
    {
        super(By.id("greenhopper-license"), GreenHopperLicenseDetailsPage.class);
    }
}
