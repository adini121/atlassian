package com.atlassian.pageobjects;

/**
 *
 */
public interface TestedProduct<T extends Tester>
{
    /**
     * Constructs the page object, changes the browser URL to the desired page URL, then binds the object to the page.
     * @param pageClass The page class
     * @param args Arguments to pass to the page object constructor.
     * @param <P> The page type
     * @return The constructed and fully loaded page with the browser set accordingly
     */
    <P extends Page> P visit(Class<P> pageClass, Object... args);

    PageBinder getPageBinder();
    ProductInstance getProductInstance();
    T getTester();
}
