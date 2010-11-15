package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.Linkable;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.product.TestedProduct;
import org.apache.commons.lang.Validate;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
@Deprecated
public class Menu<T extends TestedProduct> extends AbstractComponent<T, Menu>
{

    //private final T testedProduct;

    public Menu(T testedProduct)
    {
       super(testedProduct);
    }
}