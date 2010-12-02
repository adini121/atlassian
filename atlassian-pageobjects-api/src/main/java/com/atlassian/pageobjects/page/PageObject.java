package com.atlassian.pageobjects.page;

import com.atlassian.pageobjects.Linkable;
import com.atlassian.pageobjects.product.TestedProduct;

/**
 * The implementation for a PageObject
 */
public interface PageObject<P extends PageObject> extends Linkable
{
    P get(boolean activated);

    void doWait();
    void doCheck(String uri, boolean activated);

    TestedProduct getTestedProduct();

    void setQueryString(QueryString queryString);
}