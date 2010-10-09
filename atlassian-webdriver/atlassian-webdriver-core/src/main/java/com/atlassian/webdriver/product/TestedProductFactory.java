package com.atlassian.webdriver.product;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

/**
 *
 */
public class TestedProductFactory
{
    public static <P extends TestedProduct> P create(Class<P> testedProductClass)
    {
        return create(testedProductClass, getDefaultInstanceId(testedProductClass));
    }

    public static <P extends TestedProduct> P create(Class<P> testedProductClass, WebDriver webDriver)
    {
        return create(testedProductClass, getDefaultInstanceId(testedProductClass), webDriver);
    }

    public static <P extends TestedProduct> P create(Class<P> testedProductClass, String instanceId)
    {
        return create(testedProductClass, instanceId, AtlassianWebDriver.getDriver());
    }
    public static <P extends TestedProduct> P create(Class<P> testedProductClass, String instanceId, WebDriver webDriver)
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
            Defaults defaults = testedProductClass.getAnnotation(Defaults.class);
            httpPort = defaults.httpPort();
            contextPath = defaults.contextPath();
            baseUrl = "http://" + getLocalHostName() + ":" + httpPort + contextPath;
        }
        instance = new ProductInstance(instanceId, httpPort, contextPath, baseUrl);
        return create(testedProductClass, instance, webDriver);
    }

    private static String getDefaultInstanceId(Class<?> testedProductClass)
    {
        return testedProductClass.getAnnotation(Defaults.class).instanceId();
    }

    private static <P extends TestedProduct> P create(Class<P> testedProductClass, ProductInstance instance, WebDriver webDriver) {
        try
        {
            Constructor<P> c = testedProductClass.getConstructor(WebDriver.class, ProductInstance.class);
            return c.newInstance(webDriver, instance);
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

    public static String getLocalHostName()
    {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
