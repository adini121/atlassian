package com.atlassian.pageobjects.page;

/**
 * A login page that logs in a user and sends the browser to the next page
 */
public interface LoginPage extends Page
{
    /**
     * Logs in a user and sends the browser to the next page
     *
     * @param user     The user to login
     * @param nextPage The next page to visit, which may involve changing the URL.  Cannot be null.
     * @param <M>      The page type
     * @return The next page, fully loaded and initialized.
     */
    <M extends Page> M login(User user, Class<M> nextPage);

    /**
     * Logs in the default sysadmin user and sends the browser to the next page
     *
     * @param nextPage The next page to visit, which may involve changing the URL.  Cannot be null.
     * @param <M>      The page type
     * @return The next page, fully loaded and initialized.
     */
    <M extends Page> M loginAsSysAdmin(Class<M> nextPage);

}
