package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.DelayedBinder;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.TestedProduct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 *
 */
public class WebDriverPageBinder<T extends WebDriver> implements PageBinder
{
    private final InjectPageBinder delegate;

    public WebDriverPageBinder(final TestedProduct<WebDriverTester> testedProduct)
    {
        this.delegate = new InjectPageBinder(testedProduct, new InjectPageBinder.PostInjectionProcessor()
        {
            public <P> P process(P pageObject)
            {
                PageFactory.initElements(testedProduct.getTester().getDriver(), pageObject);
                return pageObject;
            }
        });
    }

    public <P extends Page> P navigateToAndBind(Class<P> pageClass, Object... args)
    {
        return delegate.navigateToAndBind(pageClass, args);
    }

    public <P> P bind(Class<P> pageClass, Object... args)
    {
        return delegate.bind(pageClass, args);
    }

    public <P> DelayedBinder<P> delayedBind(Class<P> pageClass, Object... args)
    {
        return delegate.delayedBind(pageClass, args);
    }

    public <P> void override(Class<P> oldClass, Class<? extends P> newClass)
    {
        delegate.override(oldClass, newClass);
    }
}
