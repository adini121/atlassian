package com.atlassian.pageobjects.framework.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.framework.ElementPageBinder;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.pageobjects.framework.element.ElementBy;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

/**
 * Represents the jquery.html
 */
public class ElementsPage implements Page
{
    @Inject
    protected ElementPageBinder pageBinder;

    @ElementBy(id="test1_addElementsButton")
    private Element test1_addElementsButton;

    @ElementBy(id="test1_delayedSpan")
    private Element test1_delayedSpan;

    public String getUrl() {
        return "/html/elements.html";
    }

    @WaitUntil
    public void doWait()
    {
        pageBinder.find(ByJquery.$("h1:contains(Html Elements Page)")).timed().isPresent().waitFor(true);
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
