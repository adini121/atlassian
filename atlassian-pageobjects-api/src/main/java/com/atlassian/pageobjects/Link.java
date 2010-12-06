package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.TestedProduct;

/**
 *
 */
public interface Link<T extends Tester, P extends Page<T>>
{
    public P activate(PageNavigator<T> pageNavigator);

}
