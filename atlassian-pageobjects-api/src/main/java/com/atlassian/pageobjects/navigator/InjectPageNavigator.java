package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.PageObject;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class InjectPageNavigator<T extends Tester> implements PageNavigator<T>
{
    private final TestedProduct<T, ?, ?, ?> testedProduct;
    private final Map<Class<PageObject<T>>, Class<PageObject<T>>> overrides =
            new HashMap<Class<PageObject<T>>, Class<PageObject<T>>>();

    public InjectPageNavigator(TestedProduct<T,?,?,?> testedProduct)
    {
        this.testedProduct = testedProduct;
    }

    public PageObject<T> gotoPageObject(Class<PageObject<T>> pageClass, Object... args)
    {
        PageObject<T> p = create(pageClass, args);
        callLifecycleMethod(p, WaitUntil.class);
        callLifecycleMethod(p, ValidateLocation.class);
        return p;
    }

    public PageObject<T> gotoActivatedPageObject(Class<PageObject<T>> pageClass, Object... args)
    {
        PageObject<T> p = create(pageClass, args);
        callLifecycleMethod(p, ValidateLocation.class);
        return p;
    }

    public void override(Class<PageObject<T>> oldClass, Class<PageObject<T>> newClass)
    {
        overrides.put(oldClass, newClass);
    }

    PageObject<T> create(Class<PageObject<T>> pageClass, Object... args)
    {
        PageObject<T> instance = null;
        Class<PageObject<T>> actualClass = pageClass;
        if (overrides.containsKey(pageClass))
        {
            actualClass = overrides.get(pageClass);
        }

        try
        {
            instance = actualClass.newInstance();
        }
        catch (InstantiationException e)
        {
            throw new IllegalArgumentException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalArgumentException(e);
        }

        autowireInjectables(instance, args);
        postInject(instance, args);
        callLifecycleMethod(instance, Init.class);
        return instance;
    }

    protected <P extends PageObject<T>> void postInject(P instance, Object[] args)
    {}

    private <P extends PageObject<T>> void autowireInjectables(P instance, Object... args)
    {
        Map<Class<?>,Object> injectables = new HashMap<Class<?>, Object>();
        T tester = testedProduct.getTester();

        injectables.put(TestedProduct.class, testedProduct);
        injectables.put(testedProduct.getClass(), testedProduct);

        injectables.put(Tester.class, tester);
        injectables.put(tester.getClass(), tester);

        injectables.put(ProductInstance.class, testedProduct.getProductInstance());
        
        injectables.put(PageNavigator.class, this);

        injectables.putAll(tester.getInjectables());
        for (Object arg : args)
        {
            injectables.put(arg.getClass(), arg);
        }

        for (Field field : instance.getClass().getDeclaredFields())
        {
            if (field.getAnnotation(Inject.class) != null)
            {
                Object val = injectables.get(field.getType());
                if (val != null)
                {
                    try
                    {
                        field.setAccessible(true);
                        field.set(instance, val);
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Injectable for class " + field.getType() + " not found");
                }
            }
        }
    }

    private <P extends PageObject<T>> void callLifecycleMethod(P instance, Class<? extends Annotation> annotation)
    {
        for (Method method : instance.getClass().getMethods())
        {
            if (method.getAnnotation(annotation) != null)
            {
                try
                {
                    method.invoke(instance);
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
                catch (InvocationTargetException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
