package com.atlassian.pageobjects.page;

/**
 *
 */
public interface LoginPage<T> extends Page<T>
{

    <M extends Page<T>> M login(User user, Class<M> nextPage);

    <M extends Page<T>> M loginAsSysAdmin(Class<M> nextPage);
    
}
