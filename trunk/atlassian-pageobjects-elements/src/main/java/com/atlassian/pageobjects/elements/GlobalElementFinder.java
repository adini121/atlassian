package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.inject.Inject;
import java.util.List;

/**
 * {@link com.atlassian.pageobjects.elements.PageElementFinder} for the global search context (whole page).
 *
 */
public class GlobalElementFinder implements PageElementFinder {

    @Inject
    AtlassianWebDriver driver;

    @Inject
    PageBinder pageBinder;

    public PageElement find(final By by)
    {
        return pageBinder.bind(WebDriverElement.class, by);
    }

    public PageElement find(final By by, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElement.class, by, timeoutType);
    }

    public <T extends PageElement> T find(final By by, Class<T> elementClass)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), by);
    }

    public <T extends PageElement> T find(final By by, Class<T> elementClass, TimeoutType timeoutType)
    {
        return pageBinder.bind(WebDriverElementMappings.findMapping(elementClass), by, timeoutType);
    }

    public List<PageElement> findAll(final By by)
    {
        return findAll(by, TimeoutType.DEFAULT);
    }

    public List<PageElement> findAll(final By by, TimeoutType timeoutType)
    {
        return findAll(by, PageElement.class, timeoutType);
    }

    public <T extends PageElement> List<T> findAll(final By by, Class<T> elementClass)
    {
        return findAll(by, elementClass, TimeoutType.DEFAULT);
    }

    public <T extends PageElement> List<T> findAll(final By by, Class<T> elementClass, TimeoutType timeoutType)
    {
        List<T> elements = Lists.newLinkedList();
        List<WebElement> webElements = driver.findElements(by);

        for(int i = 0; i < webElements.size(); i++)
        {
            elements.add(pageBinder.bind(WebDriverElementMappings.findMapping(elementClass),
                    WebDriverLocators.list(webElements.get(i), by, i, WebDriverLocators.root()), timeoutType));
        }

        return elements;
    }
}
