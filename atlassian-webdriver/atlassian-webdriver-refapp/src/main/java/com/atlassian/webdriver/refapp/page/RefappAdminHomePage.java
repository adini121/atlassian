package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.refapp.RefappTestedProduct;

/**
 *
 */
public class RefappAdminHomePage extends RefappAbstractPage<RefappAdminHomePage> implements AdminHomePage<RefappTestedProduct, RefappAdminHomePage>
{
    protected RefappAdminHomePage(RefappTestedProduct testedProduct)
    {
        super(testedProduct, "/admin");
    }

    
}
