package com.atlassian.webdriver.testing.rule;

import com.atlassian.pageobjects.*;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Creates a TestedProductRule which acts just like a TestedProduct.
 * Allows setting the rule up in a test instead of having to define a TestedProduct in
 * an @BeforeClass or @Before rule.
 *
 * <code>
 * @Rule public TestedProductRule product = new TestedProductRule(YourTestedProduct.class);
 * </code>
 *
 * @since 2.1.0
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