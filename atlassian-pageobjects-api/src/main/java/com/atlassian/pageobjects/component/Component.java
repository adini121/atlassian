package com.atlassian.pageobjects.component;

import com.atlassian.pageobjects.product.TestedProduct;

/**
 *
 * @since v1.0
 */
public interface Component<C extends Component>
{
    void initialise();
    TestedProduct getTestedProduct();
    void doWait();
}
