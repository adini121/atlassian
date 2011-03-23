package com.atlassian.pageobjects.components.aui;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.elements.Element;
import com.atlassian.pageobjects.elements.ElementFinder;
import com.atlassian.pageobjects.components.ActivatedComponent;
import org.openqa.selenium.By;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.atlassian.pageobjects.elements.query.TimedAssertions.assertTrueByDefaultTimeout;

/**
 * Represents a DropDown built via AUI.
 *
 * This is an example of a reusable components.
 */
public class AuiDropDownMenu implements ActivatedComponent<AuiDropDownMenu>
{
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected ElementFinder elementFinder;
    
    private final By locator;
    private Element rootElement;
    private Element triggerElement;
    private Element viewElement;

    public AuiDropDownMenu(By locator)
    {
        this.locator = locator;
    }

    @Init
    public void initialize()
    {
        rootElement = elementFinder.find(locator);
        triggerElement = rootElement.find(By.className("aui-dd-trigger"));
        viewElement = rootElement.find(By.className("aui-dropdown"));
    }

    public Element getTrigger()
    {
        return triggerElement;
    }

    public Element getView()
    {
        return viewElement;
    }

    public AuiDropDownMenu open()
    {
        if(!isOpen())
        {
            triggerElement.click();
            assertTrueByDefaultTimeout(viewElement.timed().isVisible());
        }
        return this;
    }

    public AuiDropDownMenu close()
    {
        if(isOpen())
        {
            triggerElement.click();
            assertTrueByDefaultTimeout(viewElement.timed().isVisible());
        }
        return this;
    }

    public boolean isOpen()
    {
        return viewElement.isVisible();
    }

    public List<String> getItems()
    {
        List<String> items = new ArrayList<String>();
        for(Element e: viewElement.findAll(By.tagName("li")))
        {
            items.add(e.text());
        }

        return items;
    }
}
