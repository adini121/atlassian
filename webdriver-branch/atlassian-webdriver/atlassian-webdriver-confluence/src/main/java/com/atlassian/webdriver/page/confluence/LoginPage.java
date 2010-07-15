package com.atlassian.webdriver.page.confluence;

import com.atlassian.webdriver.component.user.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object implementation for the LoginPage in Confluence.
 */
public class LoginPage extends ConfluenceWebDriverPage
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

    public LoginPage(WebDriver driver)
    {
        super(driver);
    }

    public LoginPage get(boolean activated)
    {
        get(URI, activated);

        return this;
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

        return ConfluencePage.DASHBOARDPAGE.get(driver, true);
    }
}
