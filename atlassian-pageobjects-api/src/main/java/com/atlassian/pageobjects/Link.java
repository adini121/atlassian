package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.PageObject;
import com.atlassian.pageobjects.product.TestedProduct;

/**
 *
 */
public interface Link<T extends PageObject>
{
    public T activate(TestedProduct testedProduct);

}
