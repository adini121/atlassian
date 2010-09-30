package com.atlassian.webdriver.ng.product;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.ng.TestedProduct;
import com.atlassian.webdriver.ng.page.AbstractPage;
import com.atlassian.webdriver.ng.page.LoginPage;
import com.atlassian.webdriver.page.PageObject;

/**
 *
 */
public class RefappLoginPage extends LoginPage<RefappHomePage>
{
    protected RefappLoginPage(TestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public RefappHomePage login(String username, String password)
    {
        return null;
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
