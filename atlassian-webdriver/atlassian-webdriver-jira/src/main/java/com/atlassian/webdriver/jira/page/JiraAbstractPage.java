package com.atlassian.webdriver.jira.page;


import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.component.menu.AdminMenu;
import com.atlassian.webdriver.jira.component.menu.UserMenu;
import com.atlassian.webdriver.component.menu.DashboardMenu;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.UserDiscoverable;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;

/**
 * Proveds a set of common functions that a JIRA page object can do.
 * Such as getting the admin menu.
 * Sets the base url for the WebDrivePage class to use which is defined in the jira-base-url system property.
 */
public abstract class JiraAbstractPage<P extends PageObject> extends AbstractPage<JiraTestedProduct, P> implements UserDiscoverable
{
    private final String uri;

    public JiraAbstractPage(JiraTestedProduct testedProduct, String uri)
    {
        super(testedProduct);
        this.uri = uri;
    }

    public boolean isLoggedIn()
    {
        return !getDriver().findElement(By.id("header-details-user")).findElement(By.tagName("a")).getText().equals("Log In");
    }

    public boolean isLoggedInAsUser(User user)
    {
        return getDriver().findElement(By.id("header-details-user")).findElement(By.tagName("a")).getText().equals(user.getFullName());
    }

    public boolean isAdmin()
    {
        return Check.elementExists(By.cssSelector("#header #menu a#admin_link"), getDriver());
    }

    public P get(boolean activated)
    {
        super.get(uri, activated);
        return (P) this;
    }

    public DashboardMenu getDashboardMenu()
    {
        return new DashboardMenu<JiraTestedProduct>(getTestedProduct());
    }

    public AdminMenu getAdminMenu()
    {
        if (isAdmin())
        {
            return new AdminMenu(testedProduct);
        }
        else
        {
            throw new RuntimeException("Tried to get the admin menu but the current user does not have access to it.");
        }
    }

    public UserMenu getUserMenu()
    {
        if (isLoggedIn())
        {
            return new UserMenu(getTestedProduct());
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