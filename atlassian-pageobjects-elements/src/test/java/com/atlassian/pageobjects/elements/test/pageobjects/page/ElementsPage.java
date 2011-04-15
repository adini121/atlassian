package com.atlassian.pageobjects.elements.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.elements.Element;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.ElementFinder;
import com.atlassian.pageobjects.elements.query.Poller;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

/**
 * Represents the elements.html
 */
public class ElementsPage implements Page
{
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected ElementFinder elementFinder;

    @ElementBy(id="test1_addElementsButton")
    private Element test1_addElementsButton;

    @ElementBy(id="test1_delayedSpan")
    private Element test1_delayedSpan;

    public String getUrl()
    {
        return "/html/elements.html";
    }

    @WaitUntil
    public void doWait()
    {
        Poller.waitUntilTrue(elementFinder.find(ByJquery.$("h1:contains(Html Elements Page)")).timed().isPresent());
    }

    public Element test1_addElementsButton()
    {
        return test1_addElementsButton;
    }

     public Element test1_delayedSpan()
    {
        return test1_delayedSpan;
    }
}
