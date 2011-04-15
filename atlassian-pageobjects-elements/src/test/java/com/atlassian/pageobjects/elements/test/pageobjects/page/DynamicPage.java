package com.atlassian.pageobjects.elements.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.elements.Element;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.ElementFinder;

import javax.inject.Inject;

import static com.atlassian.pageobjects.elements.query.Poller.waitUntilTrue;

/**
 * Represents the dynamicpage.html page
 */
public class DynamicPage implements Page
{
    @ElementBy(id = "nameTextBox")
    Element nameTextBox;

    @ElementBy(id = "helloWorldButton")
    Element helloWorldButton;

    @ElementBy(id = "messageSpan")
    Element messageSpan;

    @ElementBy(id = "createFieldSetButton")
    Element createFieldSetButton;

    @Inject
    ElementFinder elementFinder;

    public String getUrl()
    {
         return "/html/dynamicpage.html";
    }

    public DynamicPage createFieldSet()
    {
        createFieldSetButton.click();
        waitUntilTrue(nameTextBox.timed().isVisible());
        return this;
    }

    public DynamicPage helloWorld(String name)
    {
        nameTextBox.clear().type(name);
        helloWorldButton.click();
        waitUntilTrue(messageSpan.timed().isVisible());
        return this;
    }

    public String getMessage()
    {
        return messageSpan.getText();
    }

    public ElementFinder getElementFinder()
    {
        return elementFinder;
    }
}
