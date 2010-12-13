package com.atlassian.pageobjects;

/**
 *
 */
public interface PageNavigator<T extends Tester>
{
    PageObject<T> gotoPageObject(Class<PageObject<T>> pageClass, Object... args);
    PageObject<T> gotoActivatedPageObject(Class<PageObject<T>> pageClass, Object... args);

    void override(Class<PageObject<T>> oldClass, Class<PageObject<T>> newClass);
}
