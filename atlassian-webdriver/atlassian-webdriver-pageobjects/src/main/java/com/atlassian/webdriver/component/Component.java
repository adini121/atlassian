package com.atlassian.webdriver.component;

import com.atlassian.webdriver.product.TestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

/**
 *
 * @since v1.0
 */
public interface Component<TP extends TestedProduct, C extends Component>
{
    void initialise();
    void initialise(By componentLocator);
    By getComponentLocator();
    TP getTestedProduct();
    SearchContext getContext();
    void setContext(SearchContext context);
    void doWait();

}
