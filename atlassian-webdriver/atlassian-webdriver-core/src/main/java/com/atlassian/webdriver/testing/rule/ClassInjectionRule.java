package com.atlassian.webdriver.testing.rule;

import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.pageobjects.inject.InjectionContext;
import com.atlassian.pageobjects.util.InjectingTestedProducts;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class injection rule. Access via {@link com.atlassian.webdriver.testing.rule.InjectionRules}.
 *
 * @since 2.1
 */
public final class ClassInjectionRule<T extends TestedProduct<?>> implements TestRule
{
    private final T product;

    ClassInjectionRule(Class<T> productClass)
    {
        this.product = TestedProductFactory.create(checkNotNull(productClass, "productClass"));
    }

    @Override
    public Statement apply(final Statement base, final Description description)
    {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable
            {
                if (supportsInjection())
                {
                    injectionContext().injectStatic(description.getTestClass());
                }
                base.evaluate();
            }
        };
    }

    boolean supportsInjection()
    {
        return InjectingTestedProducts.supportsInjection(product);
    }

    InjectionContext injectionContext()
    {
        return InjectingTestedProducts.asInjectionContext(product);
    }
}
