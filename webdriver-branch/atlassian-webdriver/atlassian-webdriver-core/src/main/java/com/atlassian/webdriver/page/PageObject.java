package com.atlassian.webdriver.page;

import com.atlassian.webdriver.utils.QueryString;

/**
 * The implemenation for a PageObject
 */
public interface PageObject
{
    PageObject get(boolean activated);
    void setQueryString(QueryString queryString);
}