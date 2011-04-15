package com.atlassian.pageobjects.elements.test.pageobjects.component;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.components.ActivatedComponent;
import com.atlassian.pageobjects.elements.Element;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.query.Poller;
import com.atlassian.pageobjects.elements.test.pageobjects.page.ElementsPage;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Represents the JQuery menu that is present on the jquery.html page. 
 */
public class JQueryMenu implements ActivatedComponent<JQueryMenu>
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    @ElementBy(id="elements_link")
    private Element elementsLink;

    @ElementBy(id="menu")
    private Element rootElement;

    private Element triggerElement;
    private Element viewElement;

    @Init
    public void intialize()
    {
        triggerElement = rootElement.find(By.tagName("a"));
        viewElement = rootElement.find(By.tagName("ul"));
    }


    public ElementsPage gotoElementsPage()
    {
        elementsLink.click();
        return pageBinder.bind(ElementsPage.class);
    }


    public Element getTrigger()
    {
        return triggerElement;
    }

    public Element getView()
    {
        return viewElement;
    }

    public JQueryMenu open()
    {
        if(!isOpen())
        {
            getTrigger().click();
            Poller.waitUntilTrue(getView().timed().isVisible());
        }
        return this;
    }

    public JQueryMenu close()
    {
        if(isOpen())
        {
            getTrigger().click();
            Poller.waitUntilFalse(getView().timed().isVisible());
        }

        return this;
    }

    public boolean isOpen()
    {
        return getView().isVisible();
    }
}
