package com.atlassian.webdriver.component;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.product.TestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public abstract class AbstractComponent<TP extends TestedProduct, C extends Component> implements Component<TP, C>
{
    private final TP testedProduct;
    private By componentLocator;
    private SearchContext context;

    public AbstractComponent(TP testedProduct)
    {
        this(testedProduct.getDriver(), testedProduct);
    }

    public AbstractComponent(SearchContext context, TP testedProduct)
    {
        this.context = context;
        this.testedProduct = testedProduct;
    }

    /**
     * Overide this method if you wish to provide your own get method that sets up
     * your component.
     * @param componentLocator
     */
    public void initialise(By componentLocator)
    {
        this.componentLocator = componentLocator;
        doWait();
    }

    /**
     * Base initialise method.
     */
    public void initialise()
    {
        doWait();
    }

    public TP getTestedProduct()
    {
        return testedProduct;
    }

    public By getComponentLocator()
    {
        return componentLocator;
    }

    // This is here so that not every component has to implement wait.
    public void doWait()
    {
        //Do nothing.
    }

    public AtlassianWebDriver getDriver()
    {
        return getTestedProduct().getDriver();
    }

    public SearchContext getContext()
    {
        return context;
    }

    public void setContext(SearchContext context)
    {
        this.context = context;
    }
}
