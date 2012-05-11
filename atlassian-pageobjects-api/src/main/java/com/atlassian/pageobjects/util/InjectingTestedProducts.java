package com.atlassian.pageobjects.util;

import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.inject.InjectionContext;

/**
 * Manipulating and verifying {@link com.atlassian.pageobjects.TestedProduct}s abilities to inject dependencies.
 *
 * @since 2.1
 */
public final class InjectingTestedProducts
{

    private InjectingTestedProducts()
    {
        throw new AssertionError("Don't instantiate me");
    }


    /**
     * Checks whether given product supports injection. That is whether the product itself, or the associated page
     * binder is instance of {@link com.atlassian.pageobjects.inject.InjectionContext}.
     *
     * @param product product to verify
     * @return <code>true</code> if <tt>product</tt> supports injection
     */
    public static boolean supportsInjection(TestedProduct<?> product)
    {
        if (product == null)
        {
            return false;
        }
        return product instanceof InjectionContext || product.getPageBinder() instanceof InjectionContext;
    }

    /**
     * Returns injection context associated with given <tt>product</tt>.
     *
     * @param product product to retrieve context from
     * @return associated injection context, either from the product, or its page binder
     * @throws IllegalStateException if the product is not providing injection (which can be verified by
     * calling {@link #supportsInjection(com.atlassian.pageobjects.TestedProduct)}
     */
    public static InjectionContext asInjectionContext(TestedProduct<?> product)
    {
        if (product instanceof InjectionContext)
        {
            return (InjectionContext) product;
        }
        else if (product.getPageBinder() instanceof InjectionContext)
        {
            return (InjectionContext) product.getPageBinder();
        }
        else
        {
            throw new IllegalStateException("Product <" + product + "> does not support injection");
        }
    }
}
