package com.atlassian.webdriver.product;

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
    
    ProductInstance getProductInstance();
    WebDriver getDriver();
    PageFactory getPageFactory();
}
