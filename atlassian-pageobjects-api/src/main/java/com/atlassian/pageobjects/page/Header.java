package com.atlassian.pageobjects.page;

/**
 * Header component to be implemented via an override by the products
 */
public interface Header
{
    /**
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn();

    /**
     * @param user The user to validate
     * @return True if that user is logged in. False if no user is logged in or if a different user is logged in
     */
    public boolean isLoggedInAsUser(User user);

}
