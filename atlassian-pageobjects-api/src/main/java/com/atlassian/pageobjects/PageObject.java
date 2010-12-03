package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.QueryString;
import com.atlassian.pageobjects.product.TestedProduct;

/**
 * The implementation for a PageObject
 */
public interface PageObject<T extends Tester>
{
    <P extends Page<T>> P gotoPage(Link<T, P> link);
}