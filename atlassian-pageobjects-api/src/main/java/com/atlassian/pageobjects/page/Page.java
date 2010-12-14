package com.atlassian.pageobjects.page;

/**
 * The implementation for a PageObject
 */
public interface Page
{
    /**
     * @return The URI, including query string, relative to the base url
     */
    String getUrl();
}