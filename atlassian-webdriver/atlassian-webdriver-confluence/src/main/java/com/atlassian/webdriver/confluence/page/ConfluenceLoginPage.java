package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object implementation for the LoginPage in Confluence.
 */
public class ConfluenceLoginPage extends ConfluenceAbstractPage<ConfluenceLoginPage>
    implements LoginPage<ConfluenceTestedProduct, ConfluenceLoginPage, DashboardPage>
{
    private static final String URI = "/login.action";

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

        return new DashboardPage(getTestedProduct()).get(true);
    }

    public DashboardPage login(String username, String password)
    {
        return login(new User(username, password, null));
    }

    public DashboardPage loginAsAdmin()
    {
        return login("admin", "admin");
    }
}
