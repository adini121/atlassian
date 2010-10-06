package com.atlassian.webdriver.product;

import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.product.TestedProduct;

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

    public <T extends PageObject> T create(Class<T> pageClass)
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
