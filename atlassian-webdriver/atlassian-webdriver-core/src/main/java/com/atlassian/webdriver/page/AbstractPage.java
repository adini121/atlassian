package com.atlassian.webdriver.page;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.product.ProductInstance;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.utils.QueryString;
import com.atlassian.webdriver.utils.by.ByHelper;
import com.atlassian.webdriver.utils.element.ElementLocated;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The base class that a PageObject should extend. It contains helper methods for interacting with a
 * page.
 */
public abstract class AbstractPage<TP extends TestedProduct, P extends PageObject> implements PageObject<TP, P>
{
    protected QueryString queryString;

    protected final TP testedProduct;


    public AbstractPage(TP testedProduct)
    {
        this.testedProduct = testedProduct;
        this.queryString = new QueryString();
    }

    public ProductInstance getProductInstance()
    {
        return testedProduct.getProductInstance();
    }

    public AtlassianWebDriver getDriver()
    {
        return testedProduct.getDriver();
    }

    public <T extends PageObject> T gotoPage(Link<T> link)
    {
        return link.activate(testedProduct);
    }

    public <T extends PageObject> T gotoPage(Class<T> pageClass)
    {
        return (T) testedProduct.gotoPage(pageClass);
    }


    public TP getTestedProduct()
    {
        return testedProduct;
    }
    /**
     * Query string can only be set once per page. If you try to set it twice the second time then
     * an exception will be thrown.
     */
    final public void setQueryString(QueryString queryString)
    {
        Validate.isTrue(this.queryString == null);

        this.queryString = queryString;
    }

    public void waitAndCheck(String uri, boolean activated)
    {
        doWait();
        doCheck(uri, activated);
    }

    public void doWait()
    {
        getDriver().waitUntilElementIsLocated(ByHelper.BODY_TAG);
    }

    /**
     * Checks that the driver is on the expected URI.
     * @param uri
     * @param activated
     * @throws IllegalStateException if the driver is at the wrong URI.
     */
    public void doCheck(String uri, boolean activated)
    {
        //TODO: remove the activated check as should be at the uri by now.
        if (activated && uri != null && !at(uri))
        {
            throw new IllegalStateException("Expected to be at uri: " + (testedProduct.getProductInstance().getBaseUrl() + uri) + ", instead at: " + testedProduct.getDriver().getCurrentUrl());
        }
    }

    public void get(String uri, boolean activated)
    {

        if (!activated && !at(uri))
        {
            goTo(uri);
        }

        waitAndCheck(uri, activated);

        PageFactory.initElements(getDriver(), this);
    }

    // TODO: take into account the query String

    protected boolean at(String uri)
    {
        String currentUrl = testedProduct.getDriver().getCurrentUrl();
        String updatedCurrentUrl = currentUrl.replace("!default", "");
        String urlToCheck = testedProduct.getProductInstance().getBaseUrl() + uri;

        return currentUrl.startsWith(urlToCheck) || updatedCurrentUrl.startsWith(urlToCheck);
    }

    protected void goTo(String uri)
    {
        if (queryString.size() <= 0)
        {
            testedProduct.getDriver().get(testedProduct.getProductInstance().getBaseUrl() + uri);
        }
        else
        {
            testedProduct.getDriver().get(testedProduct.getProductInstance().getBaseUrl() + uri + "?" + queryString.toString());
        }
    }

    public WebDriver driver()
    {
        return testedProduct.getDriver();
    }

    public String getBaseUrl()
    {
        return testedProduct.getProductInstance().getBaseUrl();
    }
}
