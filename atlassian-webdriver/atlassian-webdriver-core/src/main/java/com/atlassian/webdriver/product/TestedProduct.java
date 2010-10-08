package com.atlassian.webdriver.product;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public interface TestedProduct<H extends HomePage, A extends AdminHomePage, L extends LoginPage>
{
    H gotoHomePage();
    A gotoAdminHomePage();
    L gotoLoginPage();
    <P extends PageObject> P gotoPage(Class<P> pageClass);

    <P extends PageObject, Q extends P> void overridePage(Class<P> oldClass, Class<Q> newClass);

    ProductInstance getProductInstance();
    WebDriver getDriver();

    <P extends PageObject> P gotoPage(Class<P> pageClass, boolean activate);
}
