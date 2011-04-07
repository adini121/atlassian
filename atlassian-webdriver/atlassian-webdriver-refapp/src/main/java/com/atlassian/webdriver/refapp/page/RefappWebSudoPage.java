package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.page.WebSudoPage;

import javax.inject.Inject;

/**
 * Refapp WebSudo page
 */
public class RefappWebSudoPage implements WebSudoPage
{
    @Inject
    private PageBinder pageBinder;

    public <T extends Page> T confirm(Class<T> targetPage)
    {
        return confirm(null, targetPage);
    }

    public <T extends Page> T confirm(String password, Class<T> targetPage)
    {
        return pageBinder.navigateToAndBind(targetPage);
    }

    public String getUrl()
    {
        return "";
    }
}
