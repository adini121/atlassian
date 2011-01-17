package com.atlassian.pageobjects.framework.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.framework.ElementPageBinder;
import com.atlassian.pageobjects.framework.test.pageobjects.component.InlineDialog;
import com.atlassian.pageobjects.framework.test.pageobjects.component.LinksMenu;
import com.atlassian.pageobjects.framework.test.pageobjects.component.UserRoleTabs;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

/**
 * Represents the page returned by the AUIServlet plugin that is deployed to RefApp
 */
public class AuiPage implements Page
{
    @Inject
    protected ElementPageBinder pageBinder;

    @WaitUntil
    public void doWait()
    {
        pageBinder.find(ByJquery.$("h2:contains(AUIDropDown)")).timed().isPresent().waitFor(true);
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
