package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
public final class InjectPageNavigator implements PageNavigator
{
    private final TestedProduct<?, ?, ?, ?> testedProduct;
    private final Tester tester;
    private final ProductInstance productInstance;

    public static interface PostInjectionProcessor
    {
        <T> T process(T pageObject);
    }

    private static class NoOpPostInjectionProcessor implements PostInjectionProcessor
    {
        public <T> T process(T pageObject)
        {
            return pageObject;
        }
    }
    private final PostInjectionProcessor postInjectionProcessor;
    private final Map<Class, Class> overrides =
            new HashMap<Class, Class>();

    public InjectPageNavigator(TestedProduct<?, ?, ?, ?> testedProduct)
    {
        this(testedProduct, new NoOpPostInjectionProcessor());
    }

    public InjectPageNavigator(TestedProduct<?, ?, ?, ?> testedProduct, PostInjectionProcessor postInjectionProcessor)
    {
        checkNotNull(testedProduct);
        checkNotNull(testedProduct.getProductInstance());
        checkNotNull(postInjectionProcessor);
        this.testedProduct = testedProduct;
        this.tester = testedProduct.getTester();
        this.productInstance = testedProduct.getProductInstance();
        this.postInjectionProcessor = postInjectionProcessor;
    }

    public <P extends Page> P gotoPage(Class<P> pageClass, Object... args)
    {
        checkNotNull(pageClass);
        P p = create(pageClass, args);
        visitUrl(p);
        callLifecycleMethod(p, WaitUntil.class);
        callLifecycleMethod(p, ValidateLocation.class);
        return p;
    }

    protected void visitUrl(Page p)
    {
        checkNotNull(p);
        String pageUrl = p.getUrl();
        String baseUrl = productInstance.getBaseUrl();
        tester.gotoUrl(baseUrl + pageUrl);
    }

    public <P> P build(Class<P> pageClass, Object... args)
    {
        checkNotNull(pageClass);
        P p = create(pageClass, args);
        callLifecycleMethod(p, WaitUntil.class);
        callLifecycleMethod(p, ValidateLocation.class);
        return p;
    }

    public <P> void override(Class<P> oldClass, Class<? extends P> newClass)
    {
        checkNotNull(oldClass);
        checkNotNull(newClass);
        overrides.put(oldClass, newClass);
    }

    <C> C create(Class<C> pageClass, Object... args)
    {
        C instance;
        Class<C> actualClass = pageClass;
        if (overrides.containsKey(pageClass))
        {
            actualClass = (Class<C>) overrides.get(pageClass);
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
        instance = postInjectionProcessor.process(instance);
        callLifecycleMethod(instance, Init.class);
        return instance;
    }

    private void autowireInjectables(Object instance, Object... args)
    {
        Map<Class<?>,Object> injectables = new HashMap<Class<?>, Object>();

        injectables.put(TestedProduct.class, testedProduct);
        injectables.put(testedProduct.getClass(), testedProduct);

        injectables.put(Tester.class, tester);
        injectables.put(tester.getClass(), tester);

        injectables.put(ProductInstance.class, productInstance);
        
        injectables.put(PageNavigator.class, this);

        injectables.putAll(tester.getInjectables());
        for (Object arg : args)
        {
            injectables.put(arg.getClass(), arg);
        }

        for (Field field : findAllFields(instance))
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

    private Collection<Field> findAllFields(Object instance)
    {
        Map<String,Field> fields = new HashMap<String,Field>();
        Class cls = instance.getClass();
        while (cls != Object.class)
        {
            for (Field field : cls.getDeclaredFields())
            {
                if (!fields.containsKey(field.getName()))
                {
                    fields.put(field.getName(), field);
                }
            }
            cls = cls.getSuperclass();
        }
        return fields.values();
    }

    private void callLifecycleMethod(Object instance, Class<? extends Annotation> annotation)
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
