package com.atlassian.webdriver.component.jira.group;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
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