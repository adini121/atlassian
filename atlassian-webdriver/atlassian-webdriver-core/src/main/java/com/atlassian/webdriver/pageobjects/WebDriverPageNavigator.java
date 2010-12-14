package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.InjectPageNavigator;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.TestedProduct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 *
 */
public class WebDriverPageNavigator<T extends WebDriver> implements PageNavigator
{
    private final InjectPageNavigator delegate;

    public WebDriverPageNavigator(final TestedProduct<WebDriverTester<T>, ?, ?, ?> testedProduct)
    {
        this.delegate = new InjectPageNavigator(testedProduct, new InjectPageNavigator.PostInjectionProcessor()
        {
            public <P> P process(P pageObject)
            {
                PageFactory.initElements(testedProduct.getTester().getDriver(), pageObject);
                return pageObject;
            }
        });
    }

    public <P extends Page> P gotoPage(Class<P> pageClass, Object... args)
    {
        return delegate.gotoPage(pageClass, args);
    }

    public <P> P build(Class<P> pageClass, Object... args)
    {
        return delegate.build(pageClass, args);
    }

    public <P> void override(Class<P> oldClass, Class<? extends P> newClass)
    {
        delegate.override(oldClass, newClass);
    }
}
