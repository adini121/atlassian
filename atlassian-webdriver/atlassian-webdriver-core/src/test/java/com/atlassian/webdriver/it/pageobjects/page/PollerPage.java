package com.atlassian.webdriver.it.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.webdriver.poller.webdriver.function.ConditionFunction;
import com.atlassian.webdriver.poller.Poller;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.inject.Inject;

/**
 * @since 2.1.0
 */
public class PollerPage implements Page
{
    @Inject
    Poller poller;

    @Inject
    WebDriver driver;

    @FindBy(id = "dialog-one")
    WebElement dialogOneElement;

    @FindBy(id = "dialog-one-show-button")
    WebElement showDialogOneButton;

    @FindBy(id = "dialog-one-hide-button")
    WebElement hideDialogOneButton;

    public String getUrl()
    {
        return "/html/poller-test-page.html";
    }

    public PollerPage showFunctionOneElement()
    {
        WebElement el = driver.findElement(By.id("test"));
        el.getText();

        showDialogOneButton.click();

        ConditionFunction func = new ConditionFunction() {

            public Boolean apply(@Nullable final WebDriver from)
            {
                return dialogOneShowing();
            }
        };

        poller.until().element(dialogOneElement).isVisible().execute();

        return this;
    }

    public PollerPage hideFunctionOneElement()
    {
        hideDialogOneButton.click();
        return this;
    }

    public boolean dialogOneShowing()
    {
        return dialogOneElement.isDisplayed();
    }
}
