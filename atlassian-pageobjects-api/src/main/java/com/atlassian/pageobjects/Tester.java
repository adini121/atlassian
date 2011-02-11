package com.atlassian.pageobjects;

/**
 * The object used for implementing page objects
 */
public interface Tester
{
    /**
     * Goes to a URL by changing the browser's location
     * @param url The url to change to
     */
    void gotoUrl(String url);
}
