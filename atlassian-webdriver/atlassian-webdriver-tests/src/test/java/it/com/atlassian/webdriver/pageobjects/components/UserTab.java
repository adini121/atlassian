package it.com.atlassian.webdriver.pageobjects.components;

import com.atlassian.webdriver.pageobjects.component.ActivatedComponent;
import com.atlassian.webdriver.pageobjects.element.Element;
import org.openqa.selenium.By;

/**
 * Represents the UserTab of the rolestab that is present in the AUIPage.
 */
public class UserTab implements ActivatedComponent<UserTab>
{
    private final String tabTitle = "User Tab";
    private final UserRoleTabs tabs;

    public UserTab(UserRoleTabs tabs)
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

    public UserTab open()
    {
        tabs.openTab(this.tabTitle);
        return this;
    }

    public boolean isOpen()
    {
        return tabs.selectedTab().text().equals(tabTitle);
    }
}
