package com.atlassian.pageobjects.component;

/**
 * Header component to be implemented via an override by the products
 */
public interface Header
{
    /**
     * @return true if a user is logged in, false otherwise.
     */
    boolean isLoggedIn();
}
