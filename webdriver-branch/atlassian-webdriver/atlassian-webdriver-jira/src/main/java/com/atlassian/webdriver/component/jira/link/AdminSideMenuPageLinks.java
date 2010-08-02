package com.atlassian.webdriver.component.jira.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.page.jira.JiraPages;

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
    }

    public static Map<Link, Page> getPageLinks()
    {
        return pageLinks;
    }

    private AdminSideMenuPageLinks() {}
}
