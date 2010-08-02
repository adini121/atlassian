package com.atlassian.webdriver.page.greenhopper;

import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.page.greenhopper.license.GreenHopperLicenseDetailsPage;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class GreenHopperPage
{

    public static final Page<GreenHopperLicenseDetailsPage> LICENSE_DETAILS_PAGE = new Page<GreenHopperLicenseDetailsPage>(GreenHopperLicenseDetailsPage.class);

    private GreenHopperPage() {}

}
