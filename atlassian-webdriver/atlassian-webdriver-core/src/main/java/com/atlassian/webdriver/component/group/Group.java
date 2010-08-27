package com.atlassian.webdriver.component.group;

/**
 * A class for that represents a Group object in JIRA.
 */
public class Group
{

    private final String groupName;

    public Group(String name)
    {
        this.groupName = name;
    }

    public String getGroupName()
    {
        return groupName;
    }
}