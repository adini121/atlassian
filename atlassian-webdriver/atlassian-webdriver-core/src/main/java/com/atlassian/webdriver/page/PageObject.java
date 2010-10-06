package com.atlassian.webdriver.page;

import com.atlassian.webdriver.product.ProductInstance;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.QueryString;

/**
 * The implementation for a PageObject
 */
public interface PageObject<TP extends TestedProduct, P extends PageObject>
{
    P get(boolean activated);

    TP getTestedProduct();

    <T extends PageObject> T gotoPage(Link<T> link);

    void setQueryString(QueryString queryString);
}