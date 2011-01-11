package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.DelayedBinder;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.webdriver.pageobjects.element.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.atlassian.pageobjects.util.InjectUtils;

import java.lang.reflect.Field;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;

/**
 * PageBinder that can inject WebElement and WebDriverDelayedElement into fields of pageobjects
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
                injectElements(pageObject);
                return pageObject;
            }
        });

        testedProduct.getTester().getDriver().setPageBinder(this);
    }

    private void injectElements(final Object instance)
    {
        forEachFieldWithAnnotation(instance, DelayedBy.class, new InjectUtils.FieldVisitor<DelayedBy>()
        {
            public void visit(Field field, DelayedBy annotation)
            {
                Element element = createElement(annotation);
                try
                {
                    field.setAccessible(true);
                    field.set(instance, element);
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Element createElement(DelayedBy delayedBy)
    {
        By by;

        if (delayedBy.className().length() > 0)
        {
            by = By.className(delayedBy.className());
        }
        else if (delayedBy.id().length() > 0)
        {
            by = By.id(delayedBy.id());
        }
        else if (delayedBy.linkText().length() > 0)
        {
            by = By.linkText(delayedBy.linkText());
        }
        else if (delayedBy.partialLinkText().length() > 0)
        {
            by = By.partialLinkText(delayedBy.partialLinkText());
        }
        else if(delayedBy.cssSelector().length() >0)
        {
            by = By.cssSelector(delayedBy.cssSelector());
        }
        else if(delayedBy.name().length() > 0)
        {
            by = By.name(delayedBy.name());
        }
        else if(delayedBy.xpath().length() > 0)
        {
            by = By.xpath(delayedBy.xpath());
        }
        else if(delayedBy.tagName().length() > 0)
        {
            by = By.tagName(delayedBy.tagName());
        }
        else
        {
            throw new IllegalArgumentException("No selector found");
        }
        
        return bind(WebDriverDelayedElement.class, by, WebDriverDelayedElement.DEFAULT_TIMEOUT_SECONDS);
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
