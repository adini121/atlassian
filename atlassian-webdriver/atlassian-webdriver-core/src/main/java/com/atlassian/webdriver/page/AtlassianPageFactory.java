package com.atlassian.webdriver.page;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.utils.QueryString;
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

    public static PageObject get(WebDriver driver, Class clazz, QueryString queryString)
    {
        return get(driver, clazz, queryString, false);
    }

    public static <T extends PageObject> T get(WebDriver driver, Class clazz, boolean activated)
    {
        return (T) get(driver, clazz, new QueryString(), activated);
    }

    public static <T extends PageObject> T get(WebDriver driver, Class clazz, QueryString queryString, boolean activated)
    {
        T page = (T) PageFactory.initElements(driver, clazz);
        page.setQueryString(queryString);

        return (T) page.get(activated);
    }
}
