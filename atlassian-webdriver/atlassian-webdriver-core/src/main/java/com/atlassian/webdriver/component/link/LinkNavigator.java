package com.atlassian.webdriver.component.link;

import com.atlassian.webdriver.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public abstract class LinkNavigator
{
    private final WebDriver driver;

    public LinkNavigator(WebDriver driver)
    {
        this.driver = driver;
    }

    public <T extends PageObject> T activateLink(Link<T> link)
    {
        return link.activate(driver);
    }


}
