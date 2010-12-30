package com.atlassian.pageobjects;

/**
 *
 */
public interface TestedProduct<T extends Tester>
{
    <P extends Page> P visit(Class<P> pageClass);

    PageBinder getPageBinder();
    ProductInstance getProductInstance();
    T getTester();
}
