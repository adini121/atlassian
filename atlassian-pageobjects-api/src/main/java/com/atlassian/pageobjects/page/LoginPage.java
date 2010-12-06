package com.atlassian.pageobjects.page;

import com.atlassian.pageobjects.Tester;

/**
 *
 */
public interface LoginPage<T extends Tester> extends Page<T>
{

    <M extends Page<T>> M login(User user, Class<M> nextPage);

    <M extends Page<T>> M loginAsSysAdmin(Class<M> nextPage);
    
}
