package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.PageObject;
import com.atlassian.pageobjects.Tester;

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
    private final T tester;

    public InjectPageNavigator(T tester)
    {
        this.tester = tester;
    }

    public <P extends PageObject<T>> P gotoPage(Class<P> pageClass, Object... args)
    {
        return null;
    }

    public <P extends PageObject<T>> P gotoActivatedPage(Class<P> pageClass, Object... args)
    {
        return null;
    }

    public <P extends PageObject<T>, Q extends P> void override(Class<P> oldClass, Class<Q> newClass)
    {

    }

    <P extends PageObject<T>> P create(Class<P> pageClass, Object... args)
    {
        P instance = null;
        try
        {
            instance = pageClass.newInstance();
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
        callLifecycleMethod(instance, Init.class);
        return instance;
    }

    private <P extends PageObject<T>> void autowireInjectables(P instance, Object... args)
    {
        Map<Class<?>,Object> injectables = new HashMap<Class<?>, Object>(tester.getInjectables());
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
                    throw new RuntimeException("Injectable for class " + field.getType() + " not found");
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
