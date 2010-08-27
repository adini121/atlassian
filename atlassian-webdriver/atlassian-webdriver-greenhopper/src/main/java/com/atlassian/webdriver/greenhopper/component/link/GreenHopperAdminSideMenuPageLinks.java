package com.atlassian.webdriver.greenhopper.component.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.greenhopper.page.GreenHopperPage;
import com.atlassian.webdriver.page.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class GreenHopperAdminSideMenuPageLinks
{

    private static final Map<Link, Page> pageLinks = new HashMap<Link, Page>();

    static {
        pageLinks.put(GreenHopperAdminSideMenuLinks.LICENSE_DETAILS_LINK, GreenHopperPage.LICENSE_DETAILS_PAGE);
    }

    public static Map<Link, Page> getPageLinks()
    {
        return pageLinks;
    }

    private GreenHopperAdminSideMenuPageLinks(){}

}
