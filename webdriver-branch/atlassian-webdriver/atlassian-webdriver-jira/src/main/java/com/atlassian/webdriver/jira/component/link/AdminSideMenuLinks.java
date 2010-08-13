package com.atlassian.webdriver.jira.component.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.jira.page.LicenseDetailsPage;
import com.atlassian.webdriver.jira.page.ProjectsViewPage;
import com.atlassian.webdriver.jira.page.ViewAttachmentsSettingsPage;
import com.atlassian.webdriver.jira.page.user.UserBrowserPage;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AdminSideMenuLinks
{
    public static final Link<ProjectsViewPage> PROJECTS_LINK = new Link(By.id("view_projects"));
    public static final Link<ViewAttachmentsSettingsPage> ATTACHMENTS_LINK = new Link(By.id("attachments"));
    public static final Link<UserBrowserPage> USER_BROWSER_LINK = new Link(By.id("user_browser"));
    public static final Link<LicenseDetailsPage> LICENSE_DETAILS_LINK = new Link(By.id("license_details"));

    private AdminSideMenuLinks(){}

}
