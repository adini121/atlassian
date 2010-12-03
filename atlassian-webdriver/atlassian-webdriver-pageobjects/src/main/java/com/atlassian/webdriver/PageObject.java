package com.atlassian.webdriver;

import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.QueryString;

/**
 * The implementation for a PageObject
 */
public interface PageObject<TP extends TestedProduct, P extends PageObject> extends Linkable
{
    P get(boolean activated);

    void doWait();
    void doCheck(String uri, boolean activated);

    TP getTestedProduct();

    void setQueryString(QueryString queryString);
}