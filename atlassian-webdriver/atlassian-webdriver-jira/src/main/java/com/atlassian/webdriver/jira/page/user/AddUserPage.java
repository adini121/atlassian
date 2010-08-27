package com.atlassian.webdriver.jira.page.user;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.jira.page.JiraAdminWebDriverPage;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class AddUserPage extends JiraAdminWebDriverPage
{
    private static String URI = "/secure/admin/user/AddUser!default.jspa";

    @FindBy (name ="username")
    private WebElement usernameField;

    @FindBy (name = "password")
    private WebElement passwordField;

    @FindBy (name = "confirm")
    private WebElement confirmPasswordField;

    @FindBy (name = "fullname")
    private WebElement fullnameField;

    @FindBy (name = "email")
    private WebElement emailField;

    @FindBy (name = "sendEmail")
    private WebElement sendEmailCheckbox;

    @FindBy (id = "create_submit")
    private WebElement createButton;

    @FindBy (id = "cancelButton")
    private WebElement cancelButton;

    private Set<String> errors = new HashSet<String>();

    public AddUserPage(WebDriver driver)
    {
        super(driver);
    }

    public AddUserPage get(final boolean activated)
    {
        get(URI, activated);

        checkForErrors();

        return this;
    }

    private void checkForErrors()
    {
        List<WebElement> errorElements = driver.findElements(By.className(".errMsg"));

        for (WebElement errEl : errorElements)
        {
            errors.add(errEl.getText());
        }
    }

    public AddUserPage setUsername(String username)
    {
        usernameField.sendKeys(username);
        return this;
    }

    public AddUserPage setPassword(String password)
    {
        passwordField.sendKeys(password);
        return this;
    }

    public AddUserPage setConfirmPassword(String password)
    {
        confirmPasswordField.sendKeys(password);
        return this;
    }

    public AddUserPage setFullname(String fullname)
    {
        fullnameField.sendKeys(fullname);
        return this;
    }

    public AddUserPage setEmail(String email)
    {
        emailField.sendKeys(email);

        return this;
    }

    public AddUserPage sendPasswordEmail(boolean sendPasswordEmail)
    {
        if (sendEmailCheckbox.isSelected())
        {
            if (!sendPasswordEmail)
            {
                sendEmailCheckbox.toggle();
            }
        }
        else
        {
            if (sendPasswordEmail)
            {
                sendEmailCheckbox.toggle();
            }
        }

        return this;
    }

    public ViewUserPage createUser()
    {
        createButton.click();

        return JiraPages.VIEW_USER_PAGE.get(driver, true);
    }

    public AddUserPage createUserExpectingError()
    {
        createButton.click();

        return JiraPages.ADD_USER_PAGE.get(driver, true);
    }

    public UserBrowserPage cancelCreateUser()
    {
        cancelButton.click();

        return JiraPages.USERBROWSERPAGE.get(driver, true);
    }

    public boolean hasError()
    {
        return errors.size() > 0;
    }
}
