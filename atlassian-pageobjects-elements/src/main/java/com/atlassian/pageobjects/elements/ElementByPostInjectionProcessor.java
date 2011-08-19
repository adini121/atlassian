package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.PostInjectionProcessor;
import com.atlassian.pageobjects.util.InjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.internal.seleniumemulation.ElementFinder;

import javax.inject.Inject;
import java.lang.reflect.Field;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Find fields marked with @ElementBy annotation and set them to instances of WebDriverElement
 */
public class ElementByPostInjectionProcessor implements PostInjectionProcessor
{
    @Inject
    PageBinder pageBinder;
    
    @Inject
    PageElementFinder finder;

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
                // Create the element to inject
                Object value;
                if (isIterable(field))
                {
                    value = createIterable(field, annotation);
                }
                else
                {
                    value = createPageElement(field, annotation);
                }
                
                // Assign the value
                try
                {
                    field.setAccessible(true);
                    field.set(instance, value);
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Iterable<? extends PageElement> createIterable(Field field, ElementBy elementBy)
    {
        By by = getSelector(elementBy);
        Class<? extends PageElement> fieldType = getFieldType(field, elementBy);
        return new PageElementIterableImpl(finder, fieldType, by, elementBy.timeoutType());
    }

    private PageElement createPageElement(Field field, ElementBy elementBy)
    {
        By by = getSelector(elementBy);
        Class<? extends PageElement> fieldType = getFieldType(field, elementBy);
        return pageBinder.bind(WebDriverElementMappings.findMapping(fieldType), by, elementBy.timeoutType());
    }

    private By getSelector(ElementBy elementBy)
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
        return by;
    }

    /**
     * Returns the type of requested PageElement: it is the type of the field
     * overriden by the attribute 'pageElementClass' in the annotation.
     * 
     * @param field the field to inject
     * @param annotation the ElementBy annotation on the field 
     * @return the requested PageElement
     * @throws IllegalArgumentException if the field or the annotation don't extend PageElement
     */
    @SuppressWarnings({"unchecked"})
    private Class<? extends PageElement> getFieldType(Field field, ElementBy annotation) {
        Class<?> fieldType = field.getType();

        // Check whether the annotation overrides this type
        Class<? extends PageElement> annotatedType = annotation.pageElementClass();
        // Checks whether annotatedType is more specific than PageElement
        if (Iterable.class.isAssignableFrom(fieldType))
        {
            return (Class<? extends PageElement>) annotatedType;
        }
        else if (annotatedType != PageElement.class)
        {
            checkArgument(fieldType.isAssignableFrom(annotatedType), "Field type " + annotatedType.getName()
                    + " does not implement " + fieldType.getName());
            return annotatedType;
        }

        checkArgument(PageElement.class.isAssignableFrom(fieldType), "Field type " + fieldType.getName()
                + " does not implement " + PageElement.class.getName());
        return (Class<? extends PageElement>) fieldType;
    }
    
    
    private boolean isIterable(Field field) {
        return Iterable.class.isAssignableFrom(field.getType());
    }
    
    
}
