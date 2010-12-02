package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.PageObject;

/**
 *
 */
public interface Linkable
{
    <T extends PageObject> T gotoPage(Link<T> link);
}
