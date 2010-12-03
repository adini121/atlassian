package com.atlassian.pageobjects.product;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.LoginPage;

/**
 *
 */
public interface TestedProduct<T extends Tester, H extends Page<T>, A extends Page<T>, L extends LoginPage<T>>
{
    H gotoHomePage();
    A gotoAdminHomePage();
    L gotoLoginPage();

    PageNavigator getPageNavigator();
    ProductInstance getProductInstance();
    T getTester();

}
