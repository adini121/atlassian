package com.atlassian.webdriver;

/**
 *
 */
public interface Linkable
{
    <T extends PageObject> T gotoPage(Link<T> link);
}
