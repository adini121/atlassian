package com.atlassian.pageobjects.page;

/**
 * The home page for the administration section of this application
 */
public interface AdminHomePage<H extends Header> extends Page
{
    /**
     * @return The top header
     */
    H getHeader();
}
