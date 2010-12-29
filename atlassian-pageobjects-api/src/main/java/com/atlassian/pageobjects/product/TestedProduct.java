package com.atlassian.pageobjects.product;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.page.Header;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.LoginPage;

/**
 *
 */
public interface TestedProduct<T extends Tester, D extends Header, H extends HomePage<D>, A extends AdminHomePage<D>, L extends LoginPage>
{
    H gotoHomePage();
    A gotoAdminHomePage();
    L gotoLoginPage();

    PageBinder getPageBinder();
    ProductInstance getProductInstance();
    T getTester();
}
