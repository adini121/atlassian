package com.atlassian.webdriver.product;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.Component;
import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class AbstractTestedProduct<H extends HomePage, A extends AdminHomePage, L extends LoginPage> implements TestedProduct<H, A, L>
{
    private final AtlassianWebDriver atlassianWebDriver;
    private final ProductInstance productInstance;
    private final Map<Class<? extends PageObject>, Class<? extends PageObject>> pageOverrides;
    private final Map<Class<? extends Component>, Class<? extends Component>> componentOverrides;

    public AbstractTestedProduct(AtlassianWebDriver webDriver, ProductInstance productInstance)
    {
        this.atlassianWebDriver = webDriver;
        this.productInstance = productInstance;
        this.pageOverrides = new HashMap<Class<? extends PageObject>, Class<? extends PageObject>>();
        this.componentOverrides = new HashMap<Class<? extends Component>, Class<? extends Component>>();
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public AtlassianWebDriver getDriver()
    {
        return atlassianWebDriver;
    }

    public <P extends PageObject, Q extends P> void overridePage(Class<P> oldClass, Class<Q> newClass)
    {
        pageOverrides.put(oldClass, newClass);
    }

    public <C extends Component, Q extends C> void overrideComponent(Class<C> oldComponentClass, Class<Q> newComponentClass)
    {
        componentOverrides.put(oldComponentClass, newComponentClass);
    }


    public <P extends PageObject> P gotoPage(Class<P> pageClass) 
    {
        return gotoPage(pageClass, false);
    }
    public <P extends PageObject> P gotoPage(Class<P> pageClass, boolean activate)
    {
        try
        {
            Class<P> actualPageClass = (Class<P>) pageOverrides.get(pageClass);
            if (actualPageClass == null)
            {
                actualPageClass = pageClass;
            }
            Constructor[] con = actualPageClass.getConstructors();
            Constructor<P> c = actualPageClass.getConstructor(getClass());
            P page = c.newInstance(this);
            page.get(activate);
            return page;
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

    //TODO(jwilson): Add component caching, so subsequent calls to getComponent are cached if
    // the driver never navigated away from the current page.
    public <C extends Component> C getComponent(Class<C> componentClass)
    {
        return getComponent(null, getDriver(), componentClass);
    }

    public <C extends Component> C getComponent(SearchContext context, Class<C> componentClass)
    {
        return getComponent(null, context, componentClass);
    }

    public <C extends Component> C getComponent(By componentLocator, Class<C> componentClass)
    {
        return getComponent(componentLocator, getDriver(), componentClass);
    }

    public <C extends Component> C getComponent(By componentLocator, SearchContext context, Class<C> componentClass)
    {
        try
        {
            Class<C> actualComponent = (Class<C>) componentOverrides.get(componentClass);
            if (actualComponent == null)
            {
                actualComponent = componentClass;
            }

            Constructor<C> c = findTestedProductConstructor(actualComponent, getClass());
            C component = c.newInstance(this);

            component.setContext(context);

            if (componentLocator != null)
            {
                component.initialise(componentLocator);
            }
            else
            {
                component.initialise();
            }

            return component;
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

    private <C extends Component> Constructor<C> findTestedProductConstructor(Class<C> componentClass, Class tpClass) throws NoSuchMethodException
    {
        try
        {
            Constructor<C> c = componentClass.getConstructor(tpClass);
            return c;
        }
        catch (NoSuchMethodException e)
        {
            Constructor<C> constructor = componentClass.getConstructor(TestedProduct.class);
            return constructor;
        }

    }
}
