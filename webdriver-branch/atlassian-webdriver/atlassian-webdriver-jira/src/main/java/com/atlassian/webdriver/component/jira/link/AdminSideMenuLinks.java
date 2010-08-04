package com.atlassian.webdriver.component.jira.link;

import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.page.jira.ProjectsViewPage;
import com.atlassian.webdriver.page.jira.ViewAttachmentsSettingsPage;
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

    private AdminSideMenuLinks(){}

}
