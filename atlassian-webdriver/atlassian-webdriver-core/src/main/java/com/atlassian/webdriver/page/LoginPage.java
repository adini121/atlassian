package com.atlassian.webdriver.page;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.product.TestedProduct;

/**
 *
 */
public interface LoginPage<TP extends TestedProduct, P extends PageObject, M extends PageObject> extends PageObject<TP, P>
{

    M login(String username, String password);

    public M loginAsAdmin();
    
}
