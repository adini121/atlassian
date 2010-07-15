package com.atlassian.webdriver.component.confluence.macro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class UserMacro
{

    private final WebElement vCardElement;

    private final WebElement userLogoLink;
    private final WebElement userLogo;
    private final WebElement usernameLink;
    private final WebElement emailLink;

    private final String username;
    private final String fullName;
    private final String email;

    /**
     * TODO: extend by passing in the hover element
     * @param vCardElement The element that is the container for the user information
     */
    public UserMacro(WebElement vCardElement)
    {
        this.vCardElement = vCardElement;

        userLogoLink = vCardElement.findElement(By.className("userLogoLink"));
        userLogo = vCardElement.findElement(By.className("userLogo"));
        usernameLink = vCardElement.findElement(By.className("confluence-userlink"));
        emailLink = vCardElement.findElement(By.className("email"));

        username = usernameLink.getAttribute("data-username");
        fullName = usernameLink.getText();
        email = emailLink.getText();
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

}
