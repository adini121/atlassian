package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.page.PageObject;
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
