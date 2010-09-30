package com.atlassian.webdriver.ng;

import com.atlassian.webdriver.ng.page.AdminHomePage;
import com.atlassian.webdriver.ng.page.HomePage;
import com.atlassian.webdriver.ng.page.LoginPage;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public interface TestedProduct<H extends HomePage, A extends AdminHomePage, L extends LoginPage>
{
    H gotoHomePage();
    A gotoAdminHomePage();
    L gotoLoginPage();
    
    ProductInstance getProductInstance();
    WebDriver getDriver();
    PageFactory getPageFactory();
}
