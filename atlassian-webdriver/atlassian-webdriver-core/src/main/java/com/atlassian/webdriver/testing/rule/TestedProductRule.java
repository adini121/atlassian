package com.atlassian.webdriver.testing.rule;

import com.atlassian.pageobjects.*;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @since v2.1
 */
public class TestedProductRule<T extends TestedProduct<WebDriverTester>> implements MethodRule, TestedProduct<WebDriverTester>
{
    private Class<T> testedProductClass;
    private T product;

    public TestedProductRule(Class<T> testedProductClass)
    {
        this.testedProductClass = testedProductClass;
    }

    public Statement apply(final Statement base, final FrameworkMethod method, final Object target)
    {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable
            {
                product = TestedProductFactory.create(testedProductClass);
                base.evaluate();
            }
        };
    }

    public <P extends Page> P visit(Class<P> pageClass, Object... args)
    {
        return product.visit(pageClass, args);
    }

    public PageBinder getPageBinder()
    {
        return product.getPageBinder();
    }

    public ProductInstance getProductInstance()
    {
        return product.getProductInstance();
    }

    public WebDriverTester getTester()
    {
        return product.getTester();
    }

    public T getTestedProduct()
    {
        return product;
    }

}