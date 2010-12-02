package com.atlassian.pageobjects.product;

import com.atlassian.pageobjects.page.PageObject;
import com.atlassian.pageobjects.component.Component;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.LoginPage;

/**
 *
 */
public interface TestedProduct<H extends HomePage, A extends AdminHomePage, L extends LoginPage, S>
{
    H gotoHomePage();
    A gotoAdminHomePage();
    L gotoLoginPage();

    <P extends PageObject> P gotoPage(Class<P> pageClass);
    <P extends PageObject> P gotoPage(Class<P> pageClass, boolean activate);

    <P extends PageObject, Q extends PageObject> void overridePage(Class<P> oldClass, Class<Q> newClass);
    <C extends Component, Q extends Component> void overrideComponent(Class<C> oldComponentClass, Class<Q> newComponentClass);

    ProductInstance getProductInstance();
    S getSelenium();

}
