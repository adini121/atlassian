package com.atlassian.pageobjects.framework.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.framework.ElementFinder;
import com.atlassian.pageobjects.framework.test.pageobjects.component.InlineDialog;
import com.atlassian.pageobjects.framework.test.pageobjects.component.LinksMenu;
import com.atlassian.pageobjects.framework.test.pageobjects.component.UserRoleTabs;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertTrueByDefaultTimeout;

/**
 * Represents the page returned by the AUIServlet plugin that is deployed to RefApp
 */
public class AuiPage implements Page
{
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected ElementFinder elementFinder;

    @WaitUntil
    public void doWait()
    {
        assertTrueByDefaultTimeout(elementFinder.find(ByJquery.$("h2:contains(AUIDropDown)")).timed().isPresent());
    }
    
    public String getUrl()
    {
        return "/html/aui.html";
    }

    public LinksMenu openLinksMenu()
    {
        return pageBinder.bind(LinksMenu.class).open();
    }

    public InlineDialog inlineDialog()
    {
        return pageBinder.bind(InlineDialog.class);        
    }

    public InlineDialog openInlineDialog()
    {
        return pageBinder.bind(InlineDialog.class).open();
    }

    public UserRoleTabs roleTabs()
    {
        return pageBinder.bind(UserRoleTabs.class);
    }
}
