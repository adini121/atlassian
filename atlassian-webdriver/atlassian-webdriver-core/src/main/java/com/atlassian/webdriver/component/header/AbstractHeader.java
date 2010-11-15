package com.atlassian.webdriver.component.header;

import com.atlassian.webdriver.Activatable;
import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.component.Component;
import com.atlassian.webdriver.product.TestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class AbstractHeader<TP extends TestedProduct, C extends Component> extends AbstractComponent<TP, C> implements Activatable
{
    private WebElement header;

    public AbstractHeader(TP testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise(final By componentLocator)
    {
        super.initialise(componentLocator);
        header = getDriver().findElement(componentLocator);
    }

    public <T extends PageObject> T activate(final Link<T> link)
    {
        return link.activate(header, getTestedProduct());
    }

    protected WebElement getHeaderElement()
    {
        return header;
    }
}
