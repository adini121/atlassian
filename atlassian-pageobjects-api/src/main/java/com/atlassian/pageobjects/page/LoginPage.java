package com.atlassian.pageobjects.page;

import com.atlassian.pageobjects.Tester;

/**
 *
 */
public interface LoginPage extends Page
{

    <M extends Page> M login(User user, Class<M> nextPage);

    <M extends Page> M loginAsSysAdmin(Class<M> nextPage);
    
}
