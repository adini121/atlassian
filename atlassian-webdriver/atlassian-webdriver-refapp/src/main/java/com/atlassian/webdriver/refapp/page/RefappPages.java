package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.page.Page;

/**
 * All the pages of refapp.
 */
public final class RefappPages
{
    /**
     * Not for instantiation.
     */
    private RefappPages()
    {}

    public static final Page<FirstPage> FIRST_PAGE = new Page<FirstPage>(FirstPage.class);
}