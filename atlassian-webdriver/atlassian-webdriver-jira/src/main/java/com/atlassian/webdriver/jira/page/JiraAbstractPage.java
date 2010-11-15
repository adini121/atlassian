package com.atlassian.webdriver.jira.page;


import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.header.Header;
import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.header.JiraHeader;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.DashboardMenu;
import com.atlassian.webdriver.jira.component.menu.JiraUserMenu;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.UserDiscoverable;
import org.openqa.selenium.By;

/**
 * Proveds a set of common functions that a JIRA page object can do.
 * Such as getting the admin menu.
 * Sets the base url for the WebDrivePage class to use which is defined in the jira-base-url system property.
 */
public abstract class JiraAbstractPage<P extends PageObject> extends AbstractPage<JiraTestedProduct, P> implements UserDiscoverable, Header<JiraHeader>
{
    private final String uri;

    public JiraAbstractPage(JiraTestedProduct testedProduct, String uri)
    {
        super(testedProduct);
        this.uri = uri;
    }

    public boolean isLoggedIn()
    {
        return getHeader().isLoggedIn();
    }

    public boolean isLoggedInAsUser(User user)
    {
        return getHeader().isLoggedInAsUser(user);
    }

    public boolean isAdmin()
    {
        return getHeader().isAdmin();
    }

    public JiraHeader getHeader()
    {
        return getTestedProduct().getComponent(JiraHeader.class);
    }

    public P get(boolean activated)
    {
        super.get(uri, activated);
        return (P) this;
    }

    public DashboardMenu getDashboardMenu()
    {
        return getHeader().getDashboardMenu();
    }

    public AdminMenu getAdminMenu()
    {
        if (isAdmin())
        {
            return getHeader().getAdminMenu();
        }
        else
        {
            throw new RuntimeException("Tried to get the admin menu but the current user does not have access to it.");
        }
    }

    public JiraUserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return getHeader().getUserMenu();
        }
        else
        {
            throw new RuntimeException("Tried to get the user menu but the user is not logged in.");
        }
    }

    /**
     * The default doWait for JIRA is to wait for the footer to be located.
     */
    @Override
    public void doWait()
    {
        getDriver().waitUntilElementIsLocated(By.className("footer"));
    }
}