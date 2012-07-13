package com.atlassian.webdriver.testing.rule;

import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.inject.InjectionContext;
import com.atlassian.pageobjects.util.InjectingTestedProducts;
import com.google.common.base.Supplier;
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
    private final Supplier<T> product;

    ClassInjectionRule(Supplier<T> productSupplier)
    {
        this.product = checkNotNull(productSupplier, "productSupplier");
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
        return InjectingTestedProducts.supportsInjection(product.get());
    }

    InjectionContext injectionContext()
    {
        return InjectingTestedProducts.asInjectionContext(product.get());
    }
}
