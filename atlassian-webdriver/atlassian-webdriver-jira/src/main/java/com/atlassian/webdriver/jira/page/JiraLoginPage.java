package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object implementation for the LoginPage in JIRA.
 */
public class JiraLoginPage extends JiraAbstractPage<JiraLoginPage> implements LoginPage<JiraTestedProduct, JiraLoginPage, DashboardPage>
{
    private static final String URI = "/login.jsp";

    @FindBy (name = "os_username")
    private WebElement usernameField;

    @FindBy (name = "os_password")
    private WebElement passwordField;

    @FindBy (name = "os_cookie")
    private WebElement rememberMeTickBox;

    @FindBy (name = "login")
    private WebElement loginButton;

    public JiraLoginPage(JiraTestedProduct driver)
    {
        super(driver, URI);
    }

    public DashboardPage loginAsAdmin() {
        return login(new User("admin", "admin", "fullname", "email"));
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

        return getTestedProduct().gotoPage(DashboardPage.class, true);
    }


}