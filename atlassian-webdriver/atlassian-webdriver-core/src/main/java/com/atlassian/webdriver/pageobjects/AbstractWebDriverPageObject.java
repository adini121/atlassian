package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.webdriver.AtlassianWebDriver;

import javax.inject.Inject;

/**
 * Represent any Page, Component and Element that can be bound to a page.
 */
public abstract class AbstractWebDriverPageObject
{
     @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected ProductInstance productInstance;

     @Inject
    protected PageBinder pageBinder;
}
