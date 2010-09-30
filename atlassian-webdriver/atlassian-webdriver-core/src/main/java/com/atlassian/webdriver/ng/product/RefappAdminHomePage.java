package com.atlassian.webdriver.ng.product;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.ng.TestedProduct;
import com.atlassian.webdriver.ng.page.AdminHomePage;
import com.atlassian.webdriver.page.PageObject;

/**
 *
 */
public class RefappAdminHomePage extends AdminHomePage
{
    protected RefappAdminHomePage(TestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public boolean isLoggedIn()
    {
        return false;
    }

    @Override
    public boolean isLoggedInAsUser(User user)
    {
        return false;
    }

    @Override
    public boolean isAdmin()
    {
        return false;
    }

    public PageObject get(boolean activated)
    {
        return null;
    }
}
