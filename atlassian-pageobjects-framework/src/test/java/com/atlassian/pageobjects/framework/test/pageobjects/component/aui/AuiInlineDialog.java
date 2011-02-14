package com.atlassian.pageobjects.framework.test.pageobjects.component.aui;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.framework.ElementFinder;
import com.atlassian.pageobjects.framework.component.ActivatedComponent;
import com.atlassian.pageobjects.framework.element.Element;
import org.openqa.selenium.By;

import javax.inject.Inject;

import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertTrueByDefaultTimeout;

/**
 * Represents an inline dialog created via AUI.
 *
 * This is an example of a reusable component.
 */
public class AuiInlineDialog implements ActivatedComponent<AuiInlineDialog>
{
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected ElementFinder elementFinder;

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
        triggerElement = elementFinder.find(triggerLocator);
        viewElement = elementFinder.find(By.id("inline-dialog-" + identifier));
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
        assertTrueByDefaultTimeout(viewElement.timed().isVisible());
        return this;
    }

    public boolean isOpen()
    {
        return viewElement.isPresent() && viewElement.isVisible();
    }
}
