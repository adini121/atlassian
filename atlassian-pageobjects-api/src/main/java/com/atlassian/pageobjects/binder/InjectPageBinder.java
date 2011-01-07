package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.DelayedBinder;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.util.InjectUtils;
import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.atlassian.pageobjects.util.InjectUtils.forEachFieldWithAnnotation;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * Page navigator that builds page objects from classes, then injects them with dependencies and calls lifecycle methods.
 * <p/>
 * <p>The construction process is as follows:
 * <ol>
 * <li>Determine the actual class by checking for an override</li>
 * <li>Instantiate the class using a constructor that matches the passed arguments</li>
 * <li>Changes the tester to the corrent URL (if {@link #navigateToAndBind(Class, Object...)})</li>
 * <li>Inject all fields annotated with {@link Inject}, including private</li>
 * <li>Execute the supplied {@link PostInjectionProcessor}</li>
 * <li>Call all methods annotated with {@link WaitUntil}</li>
 * <li>Call all methods annotated with {@link ValidateState}</li>
 * <li>Call all methods annotated with {@link Init}</li>
 * </ol>
 * <p/>
 * <p>When going to a page via the {@link #navigateToAndBind(Class, Object...)} method, the page's URL is retrieved and navigated to
 * via {@link Tester#gotoUrl(String)} after construction and initializing but before {@link WaitUntil} methods are called.
 */
public final class InjectPageBinder implements PageBinder
{
    private final TestedProduct<?> testedProduct;
    private final Tester tester;
    private final ProductInstance productInstance;
    private static final Logger log = LoggerFactory.getLogger(InjectPageBinder.class);

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

    public InjectPageBinder(TestedProduct<?> testedProduct)
    {
        this(testedProduct, new NoOpPostInjectionProcessor());
    }

    public InjectPageBinder(TestedProduct<?> testedProduct, PostInjectionProcessor postInjectionProcessor)
    {
        checkNotNull(testedProduct);
        checkNotNull(testedProduct.getProductInstance());
        checkNotNull(postInjectionProcessor);
        this.testedProduct = testedProduct;
        this.tester = testedProduct.getTester();
        this.productInstance = testedProduct.getProductInstance();
        this.postInjectionProcessor = postInjectionProcessor;
    }

    public <P extends Page> P navigateToAndBind(Class<P> pageClass, Object... args)
    {
        checkNotNull(pageClass);
        DelayedBinder<P> binder = delayedBind(pageClass, args);
        P p = binder.get();
        visitUrl(p);
        return binder.bind();
    }

    public <P> P bind(Class<P> pageClass, Object... args)
    {
        checkNotNull(pageClass);
        return delayedBind(pageClass, args).bind();
    }

    public <P> DelayedBinder<P> delayedBind(Class<P> pageClass, Object... args)
    {
        return new InjectableDelayedBind<P>(asList(
                new InstantiatePhase<P>(pageClass, args),
                new InjectPhase<P>(),
                new WaitUntilPhase<P>(),
                new ValidateStatePhase<P>(),
                new InitializePhase<P>()));
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

    private void callLifecycleMethod(Object instance, Class<? extends Annotation> annotation) throws InvocationTargetException
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
            }
        }
    }

    private static interface Phase<T>
    {
        T execute(T pageObject);
    }

    private class InstantiatePhase<T> implements Phase<T>
    {
        private Class<T> pageClass;
        private final Object[] args;

        public InstantiatePhase(Class<T> pageClass, Object[] args)
        {
            this.pageClass = pageClass;
            this.args = args;
        }

        @SuppressWarnings("unchecked")
        public T execute(T t)
        {
            T instance;
            Class<T> actualClass = pageClass;
            if (overrides.containsKey(pageClass))
            {
                actualClass = (Class<T>) overrides.get(pageClass);
            }

            try
            {
                instance = instantiate(actualClass, args);
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

        @SuppressWarnings("unchecked")
        private T instantiate(Class<T> clazz, Object[] args)
                throws InstantiationException, IllegalAccessException, InvocationTargetException
        {
            if (args != null && args.length > 0)
            {
                for (Constructor c : clazz.getConstructors())
                {
                    Class[] paramTypes = c.getParameterTypes();
                    if (args.length == paramTypes.length)
                    {
                        boolean match = true;
                        for (int x = 0; x < args.length; x++)
                        {
                            if (args[x] != null && !ClassUtils.isAssignable(args[x].getClass(), paramTypes[x], true /*autoboxing*/))
                            {
                                match = false;
                                break;
                            }
                        }
                        if (match)
                        {
                            return (T) c.newInstance(args);
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
    }

    private class InjectPhase<T> implements Phase<T>
    {
        public T execute(T t)
        {
            autowireInjectables(t);
            T pageObject = postInjectionProcessor.process(t);
            return pageObject;
        }

        private void autowireInjectables(final Object instance)
        {
            final Map<Class<?>, Object> injectables = new HashMap<Class<?>, Object>();

            injectables.put(TestedProduct.class, testedProduct);
            injectables.put(testedProduct.getClass(), testedProduct);

            injectables.put(Tester.class, tester);
            injectables.put(tester.getClass(), tester);

            injectables.put(ProductInstance.class, productInstance);

            injectables.put(PageBinder.class, InjectPageBinder.this);

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
    }

    private class WaitUntilPhase<T> implements Phase<T>
    {
        public T execute(T pageObject)
        {
            try
            {
                callLifecycleMethod(pageObject, WaitUntil.class);
            }
            catch (InvocationTargetException e)
            {
                Throwable targetException = e.getTargetException();
                if (targetException instanceof PageBindingWaitException)
                {
                    throw (PageBindingWaitException) targetException;
                }
                else
                {
                    throw new PageBindingWaitException(pageObject, targetException);
                }
            }
            return pageObject;
        }
    }

    private class ValidateStatePhase<T> implements Phase<T>
    {
        public T execute(T pageObject)
        {
            try
            {
                callLifecycleMethod(pageObject, ValidateState.class);
            }
            catch (InvocationTargetException e)
            {
                Throwable targetException = e.getTargetException();
                if (targetException instanceof InvalidPageStateException)
                {
                    throw (InvalidPageStateException) targetException;
                }
                else
                {
                    throw new InvalidPageStateException(pageObject, targetException);
                }
            }
            return pageObject;
        }
    }

    private class InitializePhase<T> implements Phase<T>
    {
        public T execute(T pageObject)
        {
            try
            {
                callLifecycleMethod(pageObject, Init.class);
            }
            catch (InvocationTargetException e)
            {
                throw new PageBindingException(pageObject, e.getTargetException());
            }
            return pageObject;
        }
    }

    private class InjectableDelayedBind<T> implements DelayedBinder<T>
    {
        private final LinkedList<Phase<T>> phases;
        private T pageObject = null;

        public InjectableDelayedBind(List<Phase<T>> phases)
        {
            this.phases = new LinkedList<Phase<T>>(phases);
        }

        public boolean canBind()
        {
            try
            {
                advanceTo(ValidateStatePhase.class);
                return true;
            }
            catch (PageBindingException ex)
            {
                return false;
            }
        }

        private void advanceTo(Class<? extends Phase> phaseClass)
        {
            boolean found = false;
            for (Phase phase : phases)
            {
                if (phase.getClass() == phaseClass)
                {
                    found = true;
                }
            }

            if (found)
            {
                Phase<T> currentPhase;
                while (!phases.isEmpty())
                {
                    currentPhase = phases.removeFirst();
                    pageObject = currentPhase.execute(pageObject);
                    if (currentPhase.getClass() == phaseClass)
                    {
                        break;
                    }
                }
            }
            else
            {
                log.debug("Already advanced to state: " + phaseClass.getName());
            }
        }


        public T get()
        {
            advanceTo(InstantiatePhase.class);
            return pageObject;
        }

        public DelayedBinder<T> inject()
        {
            advanceTo(InjectPhase.class);
            return this;
        }

        public DelayedBinder<T> waitUntil()
        {
            advanceTo(WaitUntilPhase.class);
            return this;
        }

        public DelayedBinder<T> validateState()
        {
            advanceTo(ValidateStatePhase.class);
            return this;
        }

        public T bind()
        {
            advanceTo(InitializePhase.class);
            return pageObject;
        }
    }
}
