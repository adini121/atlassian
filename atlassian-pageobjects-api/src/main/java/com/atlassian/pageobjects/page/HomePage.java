package com.atlassian.pageobjects.page;

/**
 * The home page of the application
 */
public interface HomePage<H extends Header> extends Page
{
    /**
     * @return The header
     */
    H getHeader();
}
