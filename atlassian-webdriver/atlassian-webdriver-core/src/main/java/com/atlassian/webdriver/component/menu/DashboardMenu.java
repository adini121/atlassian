package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.product.TestedProduct;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class DashboardMenu<T extends TestedProduct> extends Menu<T>
{

    public DashboardMenu(T testedProduct)
    {
        super(testedProduct);
    }

}