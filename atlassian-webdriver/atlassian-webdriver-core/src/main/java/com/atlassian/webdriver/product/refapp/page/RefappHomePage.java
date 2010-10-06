package com.atlassian.webdriver.product.refapp.page;

import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.product.refapp.RefappTestedProduct;

/**
 *
 */
public class RefappHomePage extends RefappAbstractPage<RefappHomePage> implements HomePage<RefappTestedProduct, RefappHomePage>
{
    protected RefappHomePage(RefappTestedProduct testedProduct)
    {
        super(testedProduct, "");
    }

}
