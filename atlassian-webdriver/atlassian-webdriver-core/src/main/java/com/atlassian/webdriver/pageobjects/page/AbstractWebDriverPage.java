package com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.binder.ValidateState;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.by.ByHelper;

import javax.inject.Inject;

/**
 * The base class that a PageObject should extend. It contains helper methods for interacting with a
 * page.
 */
public abstract class AbstractWebDriverPage implements Page
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected ProductInstance productInstance;

    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(ByHelper.BODY_TAG);
    }

    /**
     * Checks that the driver is on the expected URI.
     * @throws IllegalStateException if the driver is at the wrong URI.
     */
    @ValidateState
    public void doCheck()
    {
        String uri = getUrl();
        //TODO: remove the activated check as should be at the uri by now.
        if (uri != null && !at(uri))
        {
            throw new IllegalStateException("Expected to be at uri: " + (productInstance.getBaseUrl() + uri) + ", instead at: " + driver.getCurrentUrl());
        }
    }


    // TODO: take into account the query String
    protected boolean at(String uri)
    {
        String currentUrl = driver.getCurrentUrl();
        String updatedCurrentUrl = currentUrl.replace("!default", "");
        String urlToCheck = productInstance.getBaseUrl() + uri;

        return currentUrl.startsWith(urlToCheck) || updatedCurrentUrl.startsWith(urlToCheck);
    }

}
