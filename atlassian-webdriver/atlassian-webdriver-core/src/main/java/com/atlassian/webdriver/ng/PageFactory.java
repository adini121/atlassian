package com.atlassian.webdriver.ng;

import com.atlassian.webdriver.ng.page.AbstractPage;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class PageFactory
{
    private final TestedProduct testedProduct;

    public PageFactory(TestedProduct testedProduct)
    {
        this.testedProduct = testedProduct;
    }

    public <T extends AbstractPage> T create(Class<T> pageClass)
    {
        try
        {
            Constructor<T> c = pageClass.getConstructor(TestedProduct.class);
            T page = c.newInstance(testedProduct);
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
