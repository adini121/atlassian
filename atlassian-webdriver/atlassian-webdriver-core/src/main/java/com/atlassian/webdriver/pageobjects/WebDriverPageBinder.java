package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.DelayedBinder;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.util.InjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;

/**
 *
 */
public class WebDriverPageBinder<T extends WebDriver> implements PageBinder
{
    private final InjectPageBinder delegate;

    public WebDriverPageBinder(final TestedProduct<WebDriverTester<T>, ?, ?, ?> testedProduct)
    {
        this.delegate = new InjectPageBinder(testedProduct, new InjectPageBinder.PostInjectionProcessor()
        {
            public <P> P process(P pageObject)
            {
                PageFactory.initElements(testedProduct.getTester().getDriver(), pageObject);
                injectWebLinks(pageObject);
                return pageObject;
            }
        });
    }

    private void injectWebLinks(final Object instance)
    {
        forEachFieldWithAnnotation(instance, ClickableLink.class, new InjectUtils.FieldVisitor<ClickableLink>()
        {
            public void visit(Field field, ClickableLink annotation)
            {
                WebDriverLink link = createLink(annotation);
                try
                {
                    field.setAccessible(true);
                    field.set(instance, link);
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    
    private WebDriverLink createLink(ClickableLink clickableLink)
    {
        By by;
        if (clickableLink.className().length() > 0)
        {
            by = By.className(clickableLink.className());
        }
        else if (clickableLink.id().length() > 0)
        {
            by = By.id(clickableLink.id());
        }
        else if (clickableLink.linkText().length() > 0)
        {
            by = By.linkText(clickableLink.linkText());
        }
        else if (clickableLink.partialLinkText().length() > 0)
        {
            by = By.partialLinkText(clickableLink.partialLinkText());
        }
        else
        {
            throw new IllegalArgumentException("No selector found");
        }

        return bind(WebDriverLink.class, by, clickableLink.nextPage());
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
