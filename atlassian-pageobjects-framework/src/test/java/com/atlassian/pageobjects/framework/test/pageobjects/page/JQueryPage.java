package com.atlassian.pageobjects.framework.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.framework.ElementPageBinder;
import com.atlassian.pageobjects.framework.test.pageobjects.component.JQueryMenu;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

/**
 * Represents the jquery.html
 */
public class JQueryPage implements Page
{
    @Inject
    protected ElementPageBinder pageBinder;

    public String getUrl()
    {
        return "/html/jquery.html";
    }

    @WaitUntil
    public void doWait()
    {
        pageBinder.find(ByJquery.$("h2:contains(JQuery DropDown Menu)")).timed().isPresent().waitFor(true);
    }

    public JQueryMenu jqueryMenu()
    {
        return pageBinder.bind(JQueryMenu.class);
    }

    public JQueryMenu openJqueryMenu() {
        return pageBinder.bind(JQueryMenu.class).open();
    }
}
