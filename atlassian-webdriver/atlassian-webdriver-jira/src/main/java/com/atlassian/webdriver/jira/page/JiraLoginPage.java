package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.page.LoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

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

    @FindBy (how = How.ID_OR_NAME, using = "login")
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