package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.TestedProduct;

/**
 *
 */
public interface Link<P extends Page>
{
    public P activate();
}
