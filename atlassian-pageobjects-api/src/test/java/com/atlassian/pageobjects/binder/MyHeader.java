package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.page.Header;
import com.atlassian.pageobjects.page.User;

/**
 *
 */
public class MyHeader implements Header
{
    public boolean isLoggedIn()
    {
        return false;
    }

    public boolean isLoggedInAsUser(User user)
    {
        return false;
    }
}
