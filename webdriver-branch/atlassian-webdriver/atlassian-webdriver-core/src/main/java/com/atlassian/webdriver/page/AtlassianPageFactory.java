package com.atlassian.webdriver.page;

import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * 
 */
public class AtlassianPageFactory
{

    private AtlassianPageFactory() {}

    public static PageObject get(WebDriver driver, Class clazz)
    {
        return get(driver, clazz, false);
    }

    public static <T extends PageObject> T get(WebDriver driver, Class clazz, boolean activated)
    {
        return (T) ((T) PageFactory.initElements(driver, clazz)).get(activated);
    }
}
