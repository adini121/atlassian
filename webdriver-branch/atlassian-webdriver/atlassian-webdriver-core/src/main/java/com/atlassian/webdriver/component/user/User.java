package com.atlassian.webdriver.component.user;

import com.atlassian.webdriver.component.group.Group;

import java.util.HashSet;
import java.util.Set;


/**
 * A class that represents a User in JIRA.
 */
public class User
{
    private final String username;
    private final String fullName;
    private final String email;
    private final String password;

    private final Set<Group> groups;

    public User(String username, String fullName, String email)
    {
        this(username, null, fullName, email);
    }

    public User(String username, String password, String fullName, String email)
    {
        this(username, password, fullName, email, new HashSet<Group>());
    }

    public User(String username, String fullName, String email, Set<Group> groups)
    {
        this(username, null, fullName, email, groups);
    }

    public User(String username, String password, String fullName, String email, Set<Group> groups)
    {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;

        this.groups = groups;

    }

    // GENERATED CODE BELOW

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getEmail()
    {
        return email;
    }

    public Set<Group> getGroups()
    {
        return groups;
    }

    @Override
    public boolean equals(final Object o)
    {

        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        final User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null)
        {
            return false;
        }
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null)
        {
            return false;
        }
        if (username != null ? !username.equals(user.username) : user.username != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}