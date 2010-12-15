package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.util.InjectUtils;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * Page navigator that builds page objects from classes, then injects them with dependencies and calls lifecycle methods.
 *
 * <p>The construction process is as follows:
 * <ol>
 *  <li>Determine the actual class by checking for an override</li>
 *  <li>Instantiate the class using a constructor that matches the passed arguments</li>
 *  <li>Changes the tester to the corrent URL (if {@link #gotoPage(Class, Object...)})</li>
 *  <li>Inject all fields annotated with {@link Inject}, including private</li>
 *  <li>Execute the supplied {@link PostInjectionProcessor}</li>
 *  <li>Call all methods annotated with {@link Init}</li>
 *  <li>Call all methods annotated with {@link WaitUntil}</li>
 *  <li>Call all methods annotated with {@link ValidateState}</li>
 * </ol>
 *
 * <p>When going to a page via the {@link #gotoPage(Class, Object...)} method, the page's URL is retrieved and navigated to
 * via {@link Tester#gotoUrl(String)} after construction and initializing but before {@link WaitUntil} methods are called.
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
        return autowireAndCallLifecycleMethods(p);
    }

    public <P> P build(Class<P> pageClass, Object... args)
    {
        checkNotNull(pageClass);
        P p = create(pageClass, args);
        return autowireAndCallLifecycleMethods(p);
    }

    private <P> P autowireAndCallLifecycleMethods(P p)
    {
        autowireInjectables(p);
        p = postInjectionProcessor.process(p);
        callLifecycleMethod(p, Init.class);
        callLifecycleMethod(p, WaitUntil.class);
        callLifecycleMethod(p, ValidateState.class);
        return p;
    }

    protected void visitUrl(Page p)
    {
        checkNotNull(p);
        String pageUrl = p.getUrl();
        String baseUrl = productInstance.getBaseUrl();
        tester.gotoUrl(baseUrl + pageUrl);
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
            instance = instatiate(actualClass, args);
        }
        catch (InstantiationException e)
        {
            throw new IllegalArgumentException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalArgumentException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new IllegalArgumentException(e.getCause());
        }

        return instance;
    }

    private <C> C instatiate(Class<C> clazz, Object[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException
    {
        if (args != null && args.length > 0)
        {
            for (Constructor c : clazz.getConstructors())
            {
                Class[] paramTypes = c.getParameterTypes();
                if (args.length == paramTypes.length)
                {
                    boolean match = true;
                    for (int x=0; x<args.length; x++) {
                        if (args[x] != null && !paramTypes[x].isAssignableFrom(args[x].getClass()))
                        {
                            match = false;
                            break;
                        }
                    }
                    if (match)
                    {
                        return (C) c.newInstance(args);
                    }
                }
            }
        }
        else
        {
            try
            {
                return clazz.newInstance();
            }
            catch (InstantiationException ex)
            {
                throw new IllegalArgumentException("Error invoking default constructor", ex);
            }
        }
        throw new IllegalArgumentException("Cannot find constructor on " + clazz + " to match args: " + asList(args));
    }

    private void autowireInjectables(final Object instance)
    {
        final Map<Class<?>,Object> injectables = new HashMap<Class<?>, Object>();

        injectables.put(TestedProduct.class, testedProduct);
        injectables.put(testedProduct.getClass(), testedProduct);

        injectables.put(Tester.class, tester);
        injectables.put(tester.getClass(), tester);

        injectables.put(ProductInstance.class, productInstance);
        
        injectables.put(PageNavigator.class, this);

        injectables.putAll(tester.getInjectables());

        forEachFieldWithAnnotation(instance, Inject.class, new InjectUtils.FieldVisitor<Inject>()
        {
            public void visit(Field field, Inject annotation)
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
        });
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
