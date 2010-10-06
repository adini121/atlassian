package com.atlassian.webdriver.jira.component.project;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class ProjectSummary
{

    private final String name;
    private final String key;
    private final WebElement url;
    private final String urlStr;
    private final String projectLead;
    private final String defaultAssignee;
    private final WebElement viewOpertaion;
    private final WebElement editOperation;
    private final WebElement deleteOperation;

    public ProjectSummary(WebElement projectViewRow)
    {
        List<WebElement> cols = projectViewRow.findElements(By.tagName("td"));

        name = cols.get(0).getText();
        key = cols.get(1).getText();

        WebElement urlEl = cols.get(2);

        if (Check.elementExists(By.tagName("a"), urlEl))
        {
            url = urlEl.findElement(By.tagName("a"));
            urlStr = url.getText();
        }
        else
        {
            url = null;
            urlStr = "";
        }

        projectLead = cols.get(3).getText();
        defaultAssignee = cols.get(4).getText();

        List<WebElement> operations = cols.get(5).findElements(By.tagName("a"));

        viewOpertaion = operations.get(0);
        editOperation = operations.get(1);
        deleteOperation = operations.get(2);
    }

    public PageObject viewProject()
    {
        throw new UnsupportedOperationException("view Project operation on ProjectSummary has not been implemented");
    }

    public PageObject editProject()
    {
        throw new UnsupportedOperationException("edit project operation on ProjectSummary has not been implemented");
    }

    public PageObject deleteProject()
    {
        throw new UnsupportedOperationException("delete project operation on ProjectSummary has not been implemented");
    }

    public boolean hasUrl()
    {
        return url != null;
    }

    //
    // GENERATED CODE BELOW
    //

    public String getName()
    {
        return name;
    }

    public String getKey()
    {
        return key;
    }

    public WebElement getUrl()
    {
        return url;
    }

    public String getUrlStr()
    {
        return urlStr;
    }

    public String getProjectLead()
    {
        return projectLead;
    }

    public String getDefaultAssignee()
    {
        return defaultAssignee;
    }
}
