package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.PostInjectionProcessor;
import com.atlassian.pageobjects.util.InjectUtils;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import javax.inject.Inject;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;

/**
 * Find fields marked with @ElementBy annotation and set them to instances of WebDriverElement
 */
public class ElementByPostInjectionProcessor implements PostInjectionProcessor
{
    @Inject
    PageBinder pageBinder;

    public <T> T process(T pageObject)
    {
        injectElements(pageObject);
        return pageObject;
    }

    private void injectElements(final Object instance)
    {
        forEachFieldWithAnnotation(instance, ElementBy.class, new InjectUtils.FieldVisitor<ElementBy>()
        {
            public void visit(Field field, ElementBy annotation)
            {
                Element element = createElement(field, annotation);
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

    private Element createElement(Field field, ElementBy elementBy)
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

        if(SelectElement.class.isAssignableFrom(field.getType()))
        {
            return pageBinder.bind(WebDriverSelectElement.class, by, elementBy.timeoutType());
        }
        else if(MultiSelectElement.class.isAssignableFrom(field.getType()))
        {
            return pageBinder.bind(WebDriverMultiSelectElement.class, by, elementBy.timeoutType());
        }
        else
        {
            return pageBinder.bind(WebDriverElement.class, by, elementBy.timeoutType());
        }

    }
}
