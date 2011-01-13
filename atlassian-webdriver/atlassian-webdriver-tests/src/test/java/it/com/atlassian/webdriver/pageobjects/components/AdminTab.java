package it.com.atlassian.webdriver.pageobjects.components;

import com.atlassian.webdriver.pageobjects.component.ActivatedComponent;
import com.atlassian.webdriver.pageobjects.element.Element;
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
        return view().find(By.tagName("h4")).text();
    }

    public Element trigger()
    {
        return tabs.selectedTab();
    }

    public Element view()
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
        return tabs.selectedTab().text().equals(tabTitle);
    }
}
