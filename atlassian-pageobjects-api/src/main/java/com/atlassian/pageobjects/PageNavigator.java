package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;

/**
 *
 */
public interface PageNavigator
{
    <P extends Page> P gotoPage(Class<P> pageClass, Object... args);
    <P> P build(Class<P> pageClass, Object... args);

    <P> void override(Class<P> oldClass, Class<? extends P> newClass);
}
