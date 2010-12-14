package com.atlassian.pageobjects.page;

import com.atlassian.pageobjects.PageObject;
import com.atlassian.pageobjects.Tester;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The implementation for a PageObject
 */
public interface Page extends PageObject
{
    /**
     * @return The URI, including query string, relative to the base url
     */
    String getUrl();
}