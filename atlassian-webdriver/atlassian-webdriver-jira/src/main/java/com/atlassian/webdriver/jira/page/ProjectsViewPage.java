package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.project.ProjectSummary;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.utils.by.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ProjectsViewPage extends JiraAdminAbstractPage<ProjectsViewPage>
{

    @FindBy (id = "add_project")
    private WebElement addProjectLink;

    private final List<ProjectSummary> projects;
    
    private final static String URI = "/secure/project/ViewProjects.jspa";

    public ProjectsViewPage(JiraTestedProduct jiraTestedProduct)
    {
        super(jiraTestedProduct, URI);
        projects = new ArrayList<ProjectSummary>();
    }


    public ProjectsViewPage get(final boolean activated)
    {
        super.get(URI, activated);

        loadProjects();

        return this;
    }

    private void loadProjects()
    {

        List<WebElement> rows = getDriver().findElements(By.cssSelector("table.grid > tbody > tr"));

        // Remove the th.
        rows.remove(0);

        if (rows.get(0).getText().equals("You do not have the permissions to administer any projects, or there are none created."))
        {
            return;
        }

        for(WebElement row : rows)
        {
            projects.add(new ProjectSummary(row));
        }

    }

    public PageObject addProject()
    {
        throw new UnsupportedOperationException("addProject for ProjectViewPage has not been implemented");
    }

    public List<ProjectSummary> getProjects()
    {
        return Collections.unmodifiableList(projects);
    }
}