package com.atlassian.pageobjects;

/**
 *
 */
public interface PageNavigator<T extends Tester>
{
    <P extends PageObject<T>> P gotoPage(Class<P> pageClass, Object... args);
    <P extends PageObject<T>> P gotoActivatedPage(Class<P> pageClass, Object... args);

    <P extends PageObject<T>, Q extends P> void override(Class<P> oldClass, Class<Q> newClass);
}
