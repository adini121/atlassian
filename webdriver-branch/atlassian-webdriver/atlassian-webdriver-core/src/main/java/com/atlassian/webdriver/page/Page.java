package com.atlassian.webdriver.page;

import com.atlassian.webdriver.utils.QueryString;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Page<T extends PageObject>
{

    private Class clazz;

    public Page(Class clazz)
    {
        this.clazz = clazz;
    }

    public T get(WebDriver driver)
    {
        return get(driver, new QueryString(), false);
    }


    public T get(WebDriver driver, boolean activated)
    {
        return get(driver, new QueryString(), activated);
    }

    public T get(WebDriver driver, QueryString queryString)
    {
        return get(driver, queryString, false);
    }

    @SuppressWarnings ("unchecked")
    public T get(WebDriver driver, QueryString queryString, boolean activated)
    {
        return (T) AtlassianPageFactory.get(driver, clazz, queryString, activated);
    }

    public Class getPageClass()
    {
        return clazz;
    }


}
