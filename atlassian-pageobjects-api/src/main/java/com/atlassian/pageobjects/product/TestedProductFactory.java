package com.atlassian.pageobjects.product;

import com.atlassian.pageobjects.Tester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

/**
 *
 */
public class TestedProductFactory
{
    private static final Logger LOG = LoggerFactory.getLogger(TestedProductFactory.class);

    public static interface TesterFactory<T>
    {
        T create();
    }

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

    public static <T extends Tester, P extends TestedProduct<T,?,?,?>> P create(Class<P> testedProductClass)
    {
        return create(testedProductClass, (String) null, null);
    }

    public static <T extends Tester, P extends TestedProduct<T,?,?,?>> P create(Class<P> testedProductClass, String instanceId, TesterFactory<T> testerFactory)
    {
        final String contextPath, baseUrl;
        final int httpPort;

        final String ampsBaseUrl = System.getProperty("baseurl." + instanceId);
        final ProductInstance instance;
        final ProductType productType;
        if (ampsBaseUrl != null)    // running within an AMPS IntegrationTestMojo invocation - read sys props for env vars
        {
            httpPort = Integer.getInteger("http." + instanceId + ".port");
            contextPath = System.getProperty("context." + instanceId + ".path");
            baseUrl = ampsBaseUrl;
        }
        else
        {
            Defaults defaults = testedProductClass.getAnnotation(Defaults.class);
            httpPort = defaults.httpPort();
            contextPath = defaults.contextPath();
            baseUrl = "http://" + getLocalHostName() + ":" + httpPort + contextPath;
        }
        instance = new ProductInstance(instanceId, httpPort, contextPath, baseUrl);
        return create(testedProductClass, instance, testerFactory);
    }

    private static String getDefaultInstanceId(Class<?> testedProductClass)
    {
        Defaults annotation = testedProductClass.getAnnotation(Defaults.class);
        if (annotation == null)
        {
            throw new IllegalArgumentException("The tested product class '" + testedProductClass.getName() + "' is missing the @Defaults annotation");
        }
        return annotation.instanceId();
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