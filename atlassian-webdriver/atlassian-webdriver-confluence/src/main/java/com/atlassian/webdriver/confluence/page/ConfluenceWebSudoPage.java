package com.atlassian.webdriver.confluence.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.page.WebSudoPage;

import javax.inject.Inject;

/**
 * Confluence WebSudo page
 */
public class ConfluenceWebSudoPage implements WebSudoPage
{
    private static final String URI = "/authenticate.action";

    @Inject
    private PageBinder pageBinder;

    @ElementBy(id="password")
    private PageElement passwordTextbox;

    @ElementBy(id="authenticateButton")
    private PageElement confirmButton;

    public String getUrl()
    {
        return URI;
    }
    
    public <T extends Page> T confirm(Class<T> targetPage)
    {
        return confirm("admin", targetPage);
    }

    public <T extends Page> T confirm(String password, Class<T> targetPage)
    {
        passwordTextbox.type(password);
        confirmButton.click();
        return pageBinder.navigateToAndBind(targetPage);
    }


}
