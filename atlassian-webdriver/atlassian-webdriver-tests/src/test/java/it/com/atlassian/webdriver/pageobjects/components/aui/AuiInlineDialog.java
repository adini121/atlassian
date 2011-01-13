package it.com.atlassian.webdriver.pageobjects.components.aui;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.binder.InvalidPageStateException;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.component.ActivatedComponent;
import com.atlassian.webdriver.pageobjects.element.Element;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Represents an inline dialog created via AUI.
 *
 * This is an example of a reusable component.
 */
public class AuiInlineDialog implements ActivatedComponent<AuiInlineDialog>
{
    @Inject
    private AtlassianWebDriver driver;

    @Inject
    private PageBinder pageBinder;

    private final By triggerLocator;
    private final String identifier;
    private Element triggerElement;
    private Element viewElement;

    public AuiInlineDialog(By triggerLocator, String identifier)
    {
        this.triggerLocator = triggerLocator;
        this.identifier = identifier;
    }

    @Init
    public void initialize()
    {
        triggerElement = driver.find(triggerLocator);
        viewElement = driver.find(By.id("inline-dialog-" + identifier));
    }

    public Element trigger()
    {
        return triggerElement;
    }

    public Element view()
    {
        return viewElement;
    }

    public AuiInlineDialog open()
    {
        triggerElement.mouseEvents().click();
        viewElement.timed().isVisible().waitFor(true);
        return this;
    }

    public boolean isOpen()
    {
        return viewElement.isPresent() && viewElement.isVisible();
    }
}
