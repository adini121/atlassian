package com.atlassian.webdriver.jira.component.header;

import com.atlassian.webdriver.component.header.AbstractHeader;
import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.DashboardMenu;
import com.atlassian.webdriver.jira.component.menu.JiraUserMenu;
import com.atlassian.webdriver.page.UserDiscoverable;
import org.openqa.selenium.By;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class JiraHeader extends AbstractHeader<JiraTestedProduct, JiraHeader> implements UserDiscoverable
{
    private final By JIRA_HEADER_LOCATOR = By.id("header");
    private final By USERNAME_LOCATOR = By.id("header-details-user-fullname");

    public JiraHeader(final JiraTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise(JIRA_HEADER_LOCATOR);
    }

    public DashboardMenu getDashboardMenu()
    {
        return getTestedProduct().getComponent(DashboardMenu.class);
    }
    public AdminMenu getAdminMenu()
    {
        return getTestedProduct().getComponent(AdminMenu.class);
    }
    public JiraUserMenu getUserMenu()
    {
        return getTestedProduct().getComponent(JiraUserMenu.class);
    }

    public boolean isLoggedIn()
    {
        return !getHeaderElement().findElement(USERNAME_LOCATOR).getText().equals("Log In");
    }

    public boolean isLoggedInAsUser(User user)
    {
        return getHeaderElement().findElement(USERNAME_LOCATOR).getText().equals(user.getFullName());
    }

    public boolean isAdmin()
    {
        return getDriver().elementExistsAt(By.id("admin_link"), getHeaderElement());
    }

}
