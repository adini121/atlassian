package com.atlassian.webdriver.page;

import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AtlassianPageFactory
{

    private AtlassianPageFactory() {}

    @SuppressWarnings ("unchecked")
    public static <T extends PageObject> T get(WebDriver driver, Page page)
    {
        return (T) get(driver, page, false);
    }

    @SuppressWarnings ("unchecked")
    public static <T extends PageObject> T get(WebDriver driver, Page page, boolean activated)
    {
        return (T) ((T) PageFactory.initElements(driver, page.getPageClass())).get(activated);
    }

}
