package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// TODO: Add the currently logged in logic.

/**
 * Page object implementation for the LoginPage in Confluence.
 */
public class ConfluenceLoginPage extends ConfluenceAbstractPage<ConfluenceLoginPage>
    implements LoginPage<ConfluenceTestedProduct, ConfluenceLoginPage, DashboardPage>
{
    public static final String URI = "/login.action";

    @FindBy (id = "os_username")
    private WebElement usernameField;

    @FindBy (id = "os_password")
    private WebElement passwordField;

    @FindBy (id = "os_cookie")
    private WebElement rememberMeTickBox;

    @FindBy (name = "loginform")
    private WebElement loginForm;

    public ConfluenceLoginPage(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }

    // Add this to handle the logout page redirecting to the login page.
    protected  ConfluenceLoginPage(ConfluenceTestedProduct testedProduct, String uri)
    {
        super(testedProduct, uri);
    }

    public DashboardPage login(User user)
    {
        return login(user, false);
    }

    public DashboardPage login(User user, boolean rememberMe)
    {
        usernameField.sendKeys(user.getUsername());
        passwordField.sendKeys(user.getPassword());

        if (rememberMe)
        {
            rememberMeTickBox.click();
        }      

        loginForm.submit();
        getTestedProduct().setLoggedInUser(user);

        return getTestedProduct().gotoPage(DashboardPage.class, true);
    }

    public DashboardPage loginAsAdmin()
    {
        return login(new User("admin", "admin", "fullname", "email"));
    }
}
