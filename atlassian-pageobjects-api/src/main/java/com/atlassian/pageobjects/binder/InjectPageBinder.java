package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.DelayedBinder;
import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.pageobjects.browser.IgnoreBrowser;
import com.atlassian.pageobjects.browser.RequireBrowser;
import com.atlassian.pageobjects.inject.InjectionContext;
import com.atlassian.pageobjects.util.BrowserUtil;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.ProvisionException;
import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.concat;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableList;

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
public final class InjectPageBinder implements PageBinder, InjectionContext
{
    private final Tester tester;
    private final ProductInstance productInstance;
    private static final Logger log = LoggerFactory.getLogger(InjectPageBinder.class);

    private final Map<Class<?>, Class<?>> overrides = new HashMap<Class<?>, Class<?>>();
    private final Injector injector;
    private final List<Binding<PostInjectionProcessor>> postInjectionProcessors;

    public InjectPageBinder(ProductInstance productInstance, Tester tester, Module... modules)
    {
        checkNotNull(productInstance);
        checkNotNull(tester);
        checkNotNull(modules);
        this.tester = tester;
        this.productInstance = productInstance;
        this.injector = Guice.createInjector(concat(asList(modules), singleton(new Module()
        {
            public void configure(Binder binder)
            {
                binder.bind(PageBinder.class).toInstance(InjectPageBinder.this);
            }
        })));
        List<Binding<PostInjectionProcessor>> procs = Lists.newArrayList();
        for (Binding binding : injector.getAllBindings().values())
        {
            if (PostInjectionProcessor.class.isAssignableFrom(binding.getKey().getTypeLiteral().getRawType()))
            {
                procs.add(binding);
            }
        }
        postInjectionProcessors = unmodifiableList(procs);
    }


    /**
     * Injector used by this binder.
     *
     * @return injector used by this binder.
     */
    public Injector injector()
    {
        return injector;
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

    /**
     * Calls all methods with the given annotation, starting with methods found in the topmost superclass, then calling
     * more specific methods in subclasses. Note that this can mean that this will attempt to call the same method
     * multiple times - once per override in the hierarchy. Will call the methods even if they're private. Skips methods
     * if they are also annotated with {@link com.atlassian.pageobjects.browser.IgnoreBrowser} (or {@link com.atlassian.pageobjects.browser.RequireBrowser}) if the current {@link Browser}
     * matches (does not match) any of the browsers listed in that annotation.
     * @param instance the page object to check for the annotation
     * @param annotation the annotation to find
     * @throws InvocationTargetException if any matching method throws any exception.
     */
    private void callLifecycleMethod(Object instance, Class<? extends Annotation> annotation) throws InvocationTargetException
    {
        Class clazz = instance.getClass();
        List<Class> classes = ClassUtils.getAllSuperclasses(clazz);
        Collections.reverse(classes);
        classes.add(clazz);

        for (Class cl : classes)
        {
            for (Method method : cl.getDeclaredMethods())
            {
                if (method.getAnnotation(annotation) != null)
                {
                    Browser currentBrowser = BrowserUtil.getCurrentBrowser();
                    if (isIgnoredBrowser(method, method.getAnnotation(IgnoreBrowser.class), currentBrowser) ||
                            !isRequiredBrowser(method, method.getAnnotation(RequireBrowser.class), currentBrowser))
                    {
                        continue;
                    }

                    try
                    {
                        if (!method.isAccessible())
                        {
                            method.setAccessible(true);
                        }

                        method.invoke(instance);
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean isRequiredBrowser(Method method, RequireBrowser requireBrowser, Browser currentBrowser)
    {
        if (requireBrowser == null)
            return true;

        for (Browser browser : requireBrowser.value())
        {
            if (browser != currentBrowser)
            {
                log.info(method.getName() + " ignored, since it requires <" + browser + ">");
                return false;
            }
        }
        return true;
    }

    private boolean isIgnoredBrowser(Method method, IgnoreBrowser ignoreBrowser, Browser currentBrowser)
    {
        if (ignoreBrowser == null)
            return false;

        for (Browser browser : ignoreBrowser.value())
        {
            if (browser == currentBrowser || browser == Browser.ALL)
            {
                log.info(method.getName() + " ignored, reason: " + ignoreBrowser.reason());
                return true;
            }
        }
        return false;
    }

    // -----------------------------------------------------------------------------------------------  InjectionContext


    @Override
    public <T> T getInstance(Class<T> type)
    {
        checkArgument(type != null, "type was null");
        try
        {
            return injector.getInstance(type);
        }
        catch (ProvisionException e)
        {
            throw new IllegalArgumentException(e);
        }
        catch (ConfigurationException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void injectStatic(final Class<?> targetClass)
    {
        injector.createChildInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                requestStaticInjection(targetClass);
            }
        });
    }

    @Override
    public void injectMembers(Object targetInstance)
    {
        injector.injectMembers(targetInstance);
    }

    // ---------------------------------------------------------------------------------------------------------- Phases

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
            T pageObject = t;
            for (Binding<PostInjectionProcessor> binding : postInjectionProcessors)
            {
                pageObject = binding.getProvider().get().process(pageObject);
            }
            return pageObject;
        }

        private void autowireInjectables(final Object instance)
        {
            try
            {
                injector.injectMembers(instance);
            }
            catch (ConfigurationException ex)
            {
                throw new IllegalArgumentException(ex);
            }
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
                advanceTo(InitializePhase.class);
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
            for (Phase<T> phase : phases)
            {
                if (phase.getClass() == phaseClass)
                {
                    found = true;
                }
            }

            if (found)
            {
                while (!phases.isEmpty())
                {
                    pageObject = phases.getFirst().execute(pageObject);
                    if (phases.removeFirst().getClass() == phaseClass)
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
