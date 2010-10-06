package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.component.project.ProjectSummary;
import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.utils.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
public class ProjectsViewPage extends JiraAdminAbstractPage
{

    @FindBy (id = "add_project")
    private WebElement addProjectLink;

    private final List<ProjectSummary> projects;
    
    private final static String URI = "/secure/project/ViewProjects.jspa";

    public ProjectsViewPage(WebDriver driver)
    {
        super(driver);
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

        WebElement projectsTable = driver.findElement(ByJquery.$("table.grid"));

        for(WebElement row : projectsTable.findElements(ByJquery.$("> tbody > tr")))
        {

            if (Check.elementExists(By.tagName("td"), row))
            {
                projects.add(new ProjectSummary(row));
            }

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
