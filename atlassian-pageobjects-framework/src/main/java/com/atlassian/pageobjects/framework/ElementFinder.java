package com.atlassian.pageobjects.framework;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.WebDriverDelayedElement;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 *
 */
public class ElementFinder
{
    @Inject
    PageBinder pageBinder;

    /**
     * Creates a WebDriverDelayedElement using the specified locator
     * @param by Locator mechanism to use
     * @return Element that waits until its present in the DOM before executing actions.
     */
    public Element find(final By by)
    {
        return pageBinder.bind(WebDriverDelayedElement.class, by);
    }
}
