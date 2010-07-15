package com.atlassian.webdriver.page;

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

    @SuppressWarnings("unchecked")
    public T get(WebDriver driver)
    {
        return (T) this.get(driver, false);
    }

    @SuppressWarnings("unchecked")
    public T get(WebDriver driver, boolean activated)
    {
        return (T) AtlassianPageFactory.get(driver, clazz, activated);
    }

}
