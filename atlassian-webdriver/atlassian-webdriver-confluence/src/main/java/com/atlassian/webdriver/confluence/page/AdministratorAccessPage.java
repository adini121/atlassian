package com.atlassian.webdriver.confluence.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AdministratorAccessPage extends ConfluenceAbstractPage
{
    public static final String URI = "/authenticate.action";

    @FindBy (id="password")
    private WebElement passwordField;

    @FindBy (name="authenticateform")
    private WebElement authenticationForm;


    public String getUrl()
    {
        return URI;
    }

    public void login(String password)
    {
        passwordField.sendKeys(password);
        authenticationForm.submit();
    }
}