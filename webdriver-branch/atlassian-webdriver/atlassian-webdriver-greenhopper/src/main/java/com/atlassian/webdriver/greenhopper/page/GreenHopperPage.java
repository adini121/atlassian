package com.atlassian.webdriver.greenhopper.page;

import com.atlassian.webdriver.greenhopper.page.license.GreenHopperLicenseDetailsPage;
import com.atlassian.webdriver.page.Page;

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
