package com.atlassian.pageobjects.page;

/**
 *
 */
public interface LoginPage<P extends PageObject> extends PageObject<P>
{

    <M extends PageObject> M login(User user, Class<M> nextPage);

    public <M extends PageObject> M loginAsAdmin(Class<M> nextPage);
    
}
