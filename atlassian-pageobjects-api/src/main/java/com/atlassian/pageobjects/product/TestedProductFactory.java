package com.atlassian.pageobjects.product;

import com.atlassian.pageobjects.Tester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

/**
 * Constructs a {@link TestedProduct}.  The {@link TestedProduct} instance is created by calling the constructor
 * with the following method signature:
 * <pre>
 *   TestedProduct(TesterFactory, ProductInstance)
 * </pre>
 */
public class TestedProductFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(TestedProductFactory.class);

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
     * @param testedProductClass The tested product class
     * @param <T> The {@link Tester} type
     * @param <P> The tested product type
     * @return The created tested product
     */
    public static <T extends Tester, P extends TestedProduct<T,?,?,?>> P create(Class<P> testedProductClass)
    {
        return create(testedProductClass, getDefaultInstanceId(testedProductClass), null);
    }

    /**
     * Creates a tested product using the passed tester factory and instance id.
     * @param testedProductClass The tested product class
     * @param instanceId The instance id
     * @param testerFactory The tester factory to use to pass to the tested product
     * @param <T> The {@link Tester} type
     * @param <P> The tested product type
     * @return The created tested product
     */
    public static <T extends Tester, P extends TestedProduct<T,?,?,?>> P create(Class<P> testedProductClass, String instanceId, TesterFactory<T> testerFactory)
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
        instance = new ProductInstance(instanceId, httpPort, contextPath, baseUrl);
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

    private static <T extends Tester, P extends TestedProduct<T,?,?,?>> P create(Class<P> testedProductClass, ProductInstance instance, TesterFactory<T> testerFactory) {
        try
        {
            Constructor<P> c = testedProductClass.getConstructor(TesterFactory.class, ProductInstance.class);
            return c.newInstance(testerFactory, instance);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        throw new RuntimeException();
    }

    private static String getLocalHostName()
    {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}