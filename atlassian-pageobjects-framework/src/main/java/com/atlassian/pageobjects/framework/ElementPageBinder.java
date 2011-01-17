package com.atlassian.pageobjects.framework;

import com.atlassian.pageobjects.DelayedBinder;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.ElementBy;
import com.atlassian.pageobjects.framework.element.WebDriverDelayedElement;
import com.atlassian.pageobjects.util.InjectUtils;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;

/**
 * PageBinder that can inject WebElement and WebDriverDelayedElement into fields of pageobjects
 */
public class ElementPageBinder implements PageBinder
{
    private final InjectPageBinder delegate;

    public ElementPageBinder(final TestedProduct<WebDriverTester> testedProduct)
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
    }

    private void injectElements(final Object instance)
    {
        forEachFieldWithAnnotation(instance, ElementBy.class, new InjectUtils.FieldVisitor<ElementBy>()
        {
            public void visit(Field field, ElementBy annotation)
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

    private Element createElement(ElementBy elementBy)
    {
        By by;

        if (elementBy.className().length() > 0)
        {
            by = By.className(elementBy.className());
        }
        else if (elementBy.id().length() > 0)
        {
            by = By.id(elementBy.id());
        }
        else if (elementBy.linkText().length() > 0)
        {
            by = By.linkText(elementBy.linkText());
        }
        else if (elementBy.partialLinkText().length() > 0)
        {
            by = By.partialLinkText(elementBy.partialLinkText());
        }
        else if(elementBy.cssSelector().length() >0)
        {
            by = By.cssSelector(elementBy.cssSelector());
        }
        else if(elementBy.name().length() > 0)
        {
            by = By.name(elementBy.name());
        }
        else if(elementBy.xpath().length() > 0)
        {
            by = By.xpath(elementBy.xpath());
        }
        else if(elementBy.tagName().length() > 0)
        {
            by = By.tagName(elementBy.tagName());
        }
        else
        {
            throw new IllegalArgumentException("No selector found");
        }
        
        return bind(WebDriverDelayedElement.class, by);
    }

    /**
     * Creates a WebDriverDelayedElement using the specified locator
     * @param by Locator mechanism to use
     * @return Element that waits until its present in the DOM before executing actions.
     */
    public Element find(final By by)
    {
        return bind(WebDriverDelayedElement.class, by);
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
