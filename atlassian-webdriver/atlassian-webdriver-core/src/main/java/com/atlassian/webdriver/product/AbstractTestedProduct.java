package com.atlassian.webdriver.product;

import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class AbstractTestedProduct<H extends HomePage, A extends AdminHomePage, L extends LoginPage> implements TestedProduct<H, A, L>
{
    private final WebDriver webDriver;
    private final ProductInstance productInstance;
    private final Map<Class<? extends PageObject>, Class<? extends PageObject>> pageOverrides;

    public AbstractTestedProduct(WebDriver webDriver, ProductInstance productInstance)
    {
        this.webDriver = webDriver;
        this.productInstance = productInstance;
        this.pageOverrides = new HashMap<Class<? extends PageObject>, Class<? extends PageObject>>();
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public WebDriver getDriver()
    {
        return webDriver;
    }

    public <P extends PageObject, Q extends P> void overridePage(Class<P> oldClass, Class<Q> newClass) {
        pageOverrides.put(oldClass, newClass);
    }

    public <P extends PageObject> P gotoPage(Class<P> pageClass) {
        try
        {
            Class<P> actualPageClass = (Class<P>) pageOverrides.get(pageClass);
            if (actualPageClass == null)
            {
                actualPageClass = pageClass;
            }
            Constructor<P> c = actualPageClass.getConstructor(TestedProduct.class);
            P page = c.newInstance(this);
            page.get(true);
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
}
