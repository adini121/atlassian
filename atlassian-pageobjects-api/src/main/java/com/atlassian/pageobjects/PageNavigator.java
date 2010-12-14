package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;

/**
 * Builds page objects and supports special page navigation
 */
public interface PageNavigator
{
    /**
     * Visits and constructs the page, changing the current browser's URL using the page's URL
     * @param pageClass The page class
     * @param args Arguments to autowire on the page.  Should have a unique type (so no String).
     * @param <P> The page type
     * @return The constructed and fully loaded page with the browser set accordingly
     */
    <P extends Page> P gotoPage(Class<P> pageClass, Object... args);

    /**
     * Builds a page object
     *
     * @param pageClass The page object class
     * @param args Arguments to autowire on the object
     * @param <P> The page type
     * @return The constructed and loaded page object with the browser set accordingly
     */
    <P> P build(Class<P> pageClass, Object... args);

    /**
     * Overrides a page object
     * @param oldClass The old class that would have normally been constructed
     * @param newClass An subclass of the old class to be substituted
     * @param <P> The old class type
     */
    <P> void override(Class<P> oldClass, Class<? extends P> newClass);
}
