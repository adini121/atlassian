package com.atlassian.pageobjects.product;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.LoginPage;

/**
 *
 */
public interface TestedProduct<T extends Tester, H extends Page, A extends Page, L extends LoginPage>
{
    H gotoHomePage();
    A gotoAdminHomePage();
    L gotoLoginPage();

    PageNavigator getPageNavigator();
    ProductInstance getProductInstance();
    T getTester();
}
