package it.com.atlassian.webdriver.pageobjects.components.aui;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.component.ActivatedComponent;
import com.atlassian.webdriver.pageobjects.element.Element;
import org.openqa.selenium.By;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a DropDown built via AUI.
 *
 * This is an example of a reusable component. 
 */
public class AuiDropDownMenu implements ActivatedComponent<AuiDropDownMenu>
{
    @Inject
    private AtlassianWebDriver driver;

    @Inject
    private PageBinder pageBinder;
    
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
        rootElement = driver.find(locator);
        triggerElement = rootElement.find(By.className("aui-dd-trigger"));
        viewElement = rootElement.find(By.className("aui-dropdown"));
    }

    public Element trigger()
    {
        return triggerElement;
    }

    public Element view()
    {
        return viewElement;
    }

    public AuiDropDownMenu open()
    {
        if(!isOpen())
        {
            triggerElement.click();
            viewElement.timed().isVisible().waitFor(true);
        }
        return this;
    }

    public AuiDropDownMenu close()
    {
        if(isOpen())
        {
            triggerElement.click();
            viewElement.timed().isVisible().waitFor(false);
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
