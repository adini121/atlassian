package com.atlassian.pageobjects.component;

/**
 * Header component to be implemented via an override by the products
 */
public interface Header
{
    /**
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn();

}
