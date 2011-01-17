package com.atlassian.pageobjects.framework.test.pageobjects.component;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.framework.component.ActivatedComponent;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.ElementBy;
import com.atlassian.pageobjects.framework.test.pageobjects.page.ElementsPage;
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


    public Element trigger()
    {
        return triggerElement;
    }

    public Element view()
    {
        return viewElement;
    }

    public JQueryMenu open()
    {
        if(!isOpen())
        {
            trigger().click();
            view().timed().isVisible().waitFor(true);
        }
        return this;
    }

    public JQueryMenu close()
    {
        if(isOpen())
        {
            trigger().click();
            view().timed().isVisible().waitFor(false);
        }

        return this;
    }

    public boolean isOpen()
    {
        return view().isVisible();
    }
}
