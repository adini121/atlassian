package com.atlassian.webdriver.page.jira;


import com.atlassian.webdriver.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * TODO: Document this file here
 */
public enum JiraPage
{
    LOGIN("com.atlassian.hosted.webdriver.jira.page.LoginPage"),
    LOGOUT("com.atlassian.hosted.webdriver.jira.page.LogoutPage"),
    DASHBOARD("com.atlassian.hosted.webdriver.jira.page.DashboardPage"),
    PLUGINS("com.atlassian.hosted.webdriver.jira.page.PluginsPage"),
    LICENSEDETAILS("com.atlassian.hosted.webdriver.jira.page.LicenseDetailsPage"),
    USERBROWSER("com.atlassian.hosted.webdriver.jira.page.UserBrowserPage");

    String className;

    JiraPage(String className)
    {
        this.className = className;
    }

    @SuppressWarnings ("unchecked")
    public <T extends Page> T get(WebDriver driver)
    {

        Class c;
        try
        {
            c = Class.forName(this.className);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return (T) ((T) PageFactory.initElements(driver, c)).get();
    }

}