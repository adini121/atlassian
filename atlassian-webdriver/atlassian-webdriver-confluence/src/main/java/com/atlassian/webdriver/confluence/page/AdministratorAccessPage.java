package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class AdministratorAccessPage extends ConfluenceAbstractPage<AdministratorAccessPage>
{
    public static final String URI = "/authenticate.action";

    @FindBy (id="password")
    private WebElement passwordField;

    @FindBy (name="authenticateform")
    private WebElement authenticationForm;

    public AdministratorAccessPage(ConfluenceTestedProduct confluenceTestedProduct)
    {
        super(confluenceTestedProduct, URI);
    }

    public void login(User user)
    {
        passwordField.sendKeys(user.getPassword());
        authenticationForm.submit();
    }
}
