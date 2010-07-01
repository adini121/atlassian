package com.atlassian.webdriver.component.menu;

import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Menu
{

    final WebDriver driver;

    public Menu(WebDriver driver)
    {
        this.driver = driver;
    }

    public final WebDriver getDriver()
    {
        return driver;
    }

}