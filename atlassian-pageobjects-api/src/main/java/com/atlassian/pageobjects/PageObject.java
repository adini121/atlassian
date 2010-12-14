package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;

/**
 * The implementation for a PageObject
 */
public interface PageObject
{
    <P extends Page> P gotoPage(Link<P> link);
}