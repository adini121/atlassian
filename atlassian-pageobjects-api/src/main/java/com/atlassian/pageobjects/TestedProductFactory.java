package com.atlassian.pageobjects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Constructs a {@link TestedProduct}.  The {@link TestedProduct} instance is created by calling the constructor
 * with the following method signature:
 * <pre>
 *   TestedProduct(TesterFactory, ProductInstance)
 * </pre>
 */
public class TestedProductFactory
{
    /**
     * A factory for {@link Tester} instances
     * @param <T> The tester type
     */
    public static interface TesterFactory<T>
    {
        /**
         * @return The tester instance to be used through the tested product
         */
        T create();
    }

    /**
     * A factory that always returns the same {@link Tester}
     * @param <T> The tester type
     */
    public static class SingletonTesterFactory<T extends Tester> implements TesterFactory<T>
    {
        private final T tester;

        public SingletonTesterFactory(T tester)
        {
            this.tester = tester;
        }

        public T create()
        {
            return tester;
        }
    }

    /**
     * Creates a tested product, allowing the instance to choose its own default {@link Tester} and instance id
     * @param testedProductClass The tested product class name
     * @return The created tested product
     */
    @SuppressWarnings("unchecked")
    public static TestedProduct<?> create(String testedProductClass)
    {
        Class<TestedProduct<?>> clazz;
        try
        {
            clazz = (Class<TestedProduct<?>>) TestedProductFactory.class.getClassLoader().loadClass(testedProductClass);
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Cannot find tested product class: " + testedProductClass);
        }
        return create(clazz);
    }

    /**
     * Creates a tested product, allowing the instance to choose its own default {@link Tester} and instance id
     * @param testedProductClass The tested product class
     * @param <P> The tested product type
     * @return The created tested product
     */
    public static <P extends TestedProduct<?>> P create(Class<P> testedProductClass)
    {
        return create(testedProductClass, getDefaultInstanceId(testedProductClass), null);
    }

    /**
     * Creates a tested product using the passed tester factory and instance id.
     * @param testedProductClass The tested product class
     * @param instanceId The instance id
     * @param testerFactory The tester factory to use to pass to the tested product
     * @param <P> The tested product type
     * @return The created tested product
     */
    public static <P extends TestedProduct<?>> P create(Class<P> testedProductClass, String instanceId, TesterFactory<?> testerFactory)
    {
        final String contextPath, baseUrl;
        final int httpPort;

        final String ampsBaseUrl = System.getProperty("baseurl." + instanceId);
        final ProductInstance instance;
        if (ampsBaseUrl != null)    // running within an AMPS IntegrationTestMojo invocation - read sys props for env vars
        {
            httpPort = Integer.getInteger("http." + instanceId + ".port");
            contextPath = System.getProperty("context." + instanceId + ".path");
            baseUrl = ampsBaseUrl;
        }
        else
        {
            Defaults defaults = getDefaultsAnnotation(testedProductClass);
            httpPort = defaults.httpPort();
            contextPath = defaults.contextPath();
            baseUrl = "http://" + getLocalHostName() + ":" + httpPort + contextPath;
        }
        instance = new DefaultProductInstance(instanceId, httpPort, contextPath, baseUrl);
        return create(testedProductClass, instance, testerFactory);
    }

    private static String getDefaultInstanceId(Class<?> testedProductClass)
    {
        Defaults annotation = getDefaultsAnnotation(testedProductClass);
        return annotation.instanceId();
    }

    private static Defaults getDefaultsAnnotation(Class<?> testedProductClass)
    {
        Defaults annotation = testedProductClass.getAnnotation(Defaults.class);
        if (annotation == null)
        {
            throw new IllegalArgumentException("The tested product class '" + testedProductClass.getName() + "' is missing the @Defaults annotation");
        }
        return annotation;
    }

    private static <P extends TestedProduct<?>> P create(Class<P> testedProductClass, ProductInstance instance, TesterFactory<?> testerFactory) {
        try
        {
            Constructor<P> c = testedProductClass.getConstructor(TesterFactory.class, ProductInstance.class);
            return c.newInstance(testerFactory, instance);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getLocalHostName()
    {
        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Information representing the product instance being tested
     */
    private static class DefaultProductInstance implements ProductInstance
    {
        private final String baseUrl;
        private final String instanceId;
        private final int httpPort;
        private final String contextPath;


        public DefaultProductInstance(final String instanceId, final int httpPort, final String contextPath, final String baseUrl)
        {
            this.instanceId = instanceId;
            this.httpPort = httpPort;
            this.contextPath = contextPath;
            this.baseUrl = baseUrl;
        }

        public String getBaseUrl()
        {
            return baseUrl.toLowerCase();
        }

        public int getHttpPort()
        {
            return httpPort;
        }

        public String getContextPath()
        {
            return contextPath;
        }

        public String getInstanceId() {
            return instanceId;
        }
    }
}