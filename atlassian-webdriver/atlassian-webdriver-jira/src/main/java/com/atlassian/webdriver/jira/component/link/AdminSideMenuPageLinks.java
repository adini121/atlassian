package com.atlassian.webdriver.jira.component.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.page.Page;

import java.util.HashMap;
import java.util.Map;

import static com.atlassian.webdriver.jira.component.link.AdminSideMenuLinks.*;

/**
 * TODO: Document this file here
 */
public class AdminSideMenuPageLinks
{
    private static final Map<Link, Page> pageLinks = new HashMap<Link, Page>();

    static {
        pageLinks.put(PROJECTS_LINK, JiraPages.PROJECTS_VIEW_PAGE);
        pageLinks.put(ATTACHMENTS_LINK, JiraPages.ATTACHMENTS_SETTINGS_VIEW_PAGE);
        pageLinks.put(USER_BROWSER_LINK, JiraPages.USERBROWSERPAGE);
        pageLinks.put(LICENSE_DETAILS_LINK, JiraPages.LICENSEDETAILSPAGE);
    }

    public static Map<Link, Page> getPageLinks()
    {
        return pageLinks;
    }

    private AdminSideMenuPageLinks() {}
}
