package com.atlassian.webdriver.confluence.page;

import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// TODO: Add the currently logged in logic.

/**
 * Page object implementation for the LoginPage in Confluence.
 */
public class ConfluenceLoginPage extends ConfluenceAbstractPage implements LoginPage
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

    public String getUrl()
    {
        return URI;
    }

    public <M extends Page> M login(User user, Class<M> nextPage)
    {
        return login(user, false, nextPage);
    }

    public <M extends Page> M loginAsSysAdmin(Class<M> nextPage)
    {
        return login(new User("admin", "admin", "fullname", "email"), nextPage);
    }

    public <M extends Page> M login(User user, boolean rememberMe, Class<M> nextPage)
    {
        usernameField.sendKeys(user.getUsername());
        passwordField.sendKeys(user.getPassword());

        if (rememberMe)
        {
            rememberMeTickBox.click();
        }      

        loginForm.submit();
        testedProduct.setLoggedInUser(user);

        return pageNavigator.gotoPage(nextPage);
    }

}
