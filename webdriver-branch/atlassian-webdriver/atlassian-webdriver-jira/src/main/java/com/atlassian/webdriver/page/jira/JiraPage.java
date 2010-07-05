package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.page.AtlassianPageFactory;
import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.WebDriver;


/**
 * Provides a enum of all the pages that are available in JIRA.
 * Using the get method a object of the page class will be returned.
 * eg. JiraPage.LOGIN.get(webdriver) will return an instance of the LoginPage object.
 */
public enum JiraPage implements Page
{
    LOGIN(LoginPage.class),
    LOGOUT(LogoutPage.class),
    DASHBOARD(DashboardPage.class),
    PLUGINS(PluginsPage.class),
    LICENSEDETAILS(LicenseDetailsPage.class),
    USERBROWSER(UserBrowserPage.class);

    Class clazz;

    JiraPage(Class clazz)
    {
        this.clazz = clazz;
    }

    public Class getPageClass()
    {
        return this.clazz;
    }

    public <T extends PageObject> T get(WebDriver driver)
    {
        return (T) this.get(driver, false);
    }

    public <T extends PageObject> T get(WebDriver driver, boolean activated)
    {
        return (T) AtlassianPageFactory.get(driver, this, activated);
    }

}