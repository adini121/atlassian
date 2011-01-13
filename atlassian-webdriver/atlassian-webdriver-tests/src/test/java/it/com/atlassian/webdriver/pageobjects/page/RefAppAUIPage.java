package it.com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.by.ByJquery;
import it.com.atlassian.webdriver.pageobjects.components.InlineDialog;
import it.com.atlassian.webdriver.pageobjects.components.LinksMenu;
import it.com.atlassian.webdriver.pageobjects.components.UserRoleTabs;

import javax.inject.Inject;

/**
 * Represents the page returned by the AUIServlet plugin that is deployed to RefApp
 */
public class RefAppAUIPage extends RefappAbstractPage
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    @WaitUntil
    public void doWait()
    {
        driver.find(ByJquery.$("h2:contains(AUIDropDown)")).timed().isPresent().waitFor(true);
    }
    
    public String getUrl()
    {
        return "/plugins/servlet/webdriver/auipage";
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
