package com.atlassian.pageobjects.elements.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.PageElements;
import com.atlassian.pageobjects.elements.query.Poller;

/**
 * Represents the elements.html
 */
public class PageElementSearchPage implements Page
{
    @ElementBy(tagName = PageElements.BODY)
    protected PageElement body;

    @Override
    public String getUrl()
    {
        return "/html/elements.html";
    }

    @WaitUntil
    public void waitForPageLoad()
    {
        Poller.waitUntilTrue(body.timed().hasAttribute("id", "page-element-search-page"));
    }
}
