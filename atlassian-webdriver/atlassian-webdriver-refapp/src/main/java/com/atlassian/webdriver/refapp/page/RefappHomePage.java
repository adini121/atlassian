package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.refapp.RefappTestedProduct;

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
