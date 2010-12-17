package com.atlassian.pageobjects;

import java.util.Map;

/**
 * The object used for implementing page objects
 */
public interface Tester
{
    /**
     * @return A map of objects to make injectable to page objects
     */
    Map<Class<?>,Object> getInjectables();

    /**
     * Goes to a URL by changing the browser's location
     * @param url The url to change to
     */
    void gotoUrl(String url);
}
