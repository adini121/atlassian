package com.atlassian.pageobjects.elements.test.pageobjects.component;

import com.atlassian.pageobjects.elements.Element;
import com.atlassian.pageobjects.components.ActivatedComponent;
import org.openqa.selenium.By;


/**
 * Represents the Admin tab of the rolestab that is present in the AUIPage.
 */
public class AdminTab implements ActivatedComponent<AdminTab>
{
    private final String tabTitle = "Admin Tab";
    private final UserRoleTabs tabs;

    public AdminTab(UserRoleTabs tabs)
    {
        this.tabs = tabs;
    }

    public String header()
    {
        return getView().find(By.tagName("h4")).getText();
    }

    public Element getTrigger()
    {
        return tabs.selectedTab();
    }

    public Element getView()
    {
        return tabs.selectedView();
    }

    public AdminTab open()
    {
        tabs.openTab(this.tabTitle);
        return this;
    }

    public boolean isOpen()
    {
        return tabs.selectedTab().getText().equals(tabTitle);
    }
}
