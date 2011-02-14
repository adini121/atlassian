package com.atlassian.pageobjects.framework.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.framework.ElementFinder;
import com.atlassian.pageobjects.framework.test.pageobjects.component.JQueryMenu;
import com.atlassian.webdriver.utils.by.ByJquery;

import javax.inject.Inject;

import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertTrueByDefaultTimeout;

/**
 * Represents the jquery.html
 */
public class JQueryPage implements Page
{
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected ElementFinder elementFinder;

    public String getUrl()
    {
        return "/html/jquery.html";
    }

    @WaitUntil
    public void doWait()
    {
        assertTrueByDefaultTimeout(elementFinder.find(ByJquery.$("h2:contains(JQuery DropDown Menu)")).timed().isPresent());
    }

    public JQueryMenu jqueryMenu()
    {
        return pageBinder.bind(JQueryMenu.class);
    }

    public JQueryMenu openJqueryMenu() {
        return pageBinder.bind(JQueryMenu.class).open();
    }
}
