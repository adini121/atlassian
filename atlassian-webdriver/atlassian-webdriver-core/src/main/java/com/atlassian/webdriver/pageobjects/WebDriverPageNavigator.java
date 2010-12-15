package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.InjectPageNavigator;
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

        return build(WebDriverLink.class, by, clickableLink.nextPage());
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
