package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.component.jira.user.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class LoginPage extends JiraWebDriverPage
{

    @FindBy (id = "usernameinput")
    private WebElement usernameField;

    @FindBy (id = "os_password")
    private WebElement passwordField;

    @FindBy (id = "os_cookie_id")
    private WebElement rememberMeTickBox;

    @FindBy (id = "login")
    private WebElement loginButton;

    public LoginPage(WebDriver driver)
    {
        super(driver);
    }

    public LoginPage get()
    {
        goTo("/login.jsp");
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

        loginButton.click();

        return JiraPage.DASHBOARD.get(driver);
    }


}