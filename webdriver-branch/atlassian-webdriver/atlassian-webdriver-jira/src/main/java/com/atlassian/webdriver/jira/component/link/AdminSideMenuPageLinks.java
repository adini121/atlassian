package com.atlassian.webdriver.jira.component.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.page.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Document this file here
 */
public class AdminSideMenuPageLinks
{
    private static final Map<Link, Page> pageLinks = new HashMap<Link, Page>();

    static {
        pageLinks.put(AdminSideMenuLinks.PROJECTS_LINK, JiraPages.PROJECTS_VIEW_PAGE);
        pageLinks.put(AdminSideMenuLinks.ATTACHMENTS_LINK, JiraPages.ATTACHMENTS_SETTINGS_VIEW_PAGE);
    }

    public static Map<Link, Page> getPageLinks()
    {
        return pageLinks;
    }

    private AdminSideMenuPageLinks() {}
}
