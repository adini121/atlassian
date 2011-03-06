package com.atlassian.pageobjects.framework.test.pageobjects.component;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.framework.component.TabbedComponent;
import com.atlassian.pageobjects.framework.component.aui.AuiTabs;
import com.atlassian.pageobjects.framework.element.Element;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;

import javax.inject.Inject;
import java.util.List;

/**
 * Represents the Role Tabs that are availalbe in the AUIPage. This page is generated by the AUIServlet plugin that is
 * installed in RefApp.
 *
 * The roles tab contains two tabs, one for User and the other for Admin
 */
public class UserRoleTabs implements TabbedComponent
{
    @Inject
    protected AtlassianWebDriver driver;

    @Inject
    protected PageBinder pageBinder;

    private AuiTabs auiTabs;

    @Init
    public void initialize()
    {
        auiTabs = pageBinder.bind(AuiTabs.class, By.id("horizontal"));
    }

    public AdminTab adminTab()
    {
        return pageBinder.bind(AdminTab.class, this);
    }

    public AdminTab openAdminTab()
    {
        return pageBinder.bind(AdminTab.class, this).open();
    }

    public UserTab userTab()
    {
        return pageBinder.bind(UserTab.class, this);
    }

    public UserTab openUserTab()
    {
        return pageBinder.bind(UserTab.class, this).open();
    }

    public Element selectedTab()
    {
        return auiTabs.selectedTab();
    }

    public Element selectedView()
    {
        return auiTabs.selectedView();
    }

    public List<Element> tabs()
    {
        return auiTabs.tabs();
    }

    public Element openTab(Element tab)
    {
        return auiTabs.openTab(tab);
    }

    public Element openTab(String titleText)
    {
        return auiTabs.openTab(titleText);
    }
}
