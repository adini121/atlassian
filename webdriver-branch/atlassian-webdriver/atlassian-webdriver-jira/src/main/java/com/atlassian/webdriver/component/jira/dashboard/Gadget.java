package com.atlassian.webdriver.component.jira.dashboard;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.utils.Check;
import com.atlassian.webdriver.utils.MouseEvents;
import com.atlassian.webdriver.utils.VisibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Gadget
{
    private final String id;
    private final String titleId;
    private final String frameId;
    private final WebElement chrome;

    private final WebDriver driver;

    public Gadget(String gadgetId, WebDriver driver)
    {
        this.driver = driver;
        this.id = gadgetId;
        this.frameId = "gadget-" + id;
        this.titleId = frameId + "-title";
        final String chromeId = frameId + "-chrome";

        // This should only get invoked when the gadget doesn't exist. If there are unexpected timing
        // issues, could change to a wait until.
        if (!Check.elementExists(By.id(titleId)))
        {
            throw new IllegalStateException("Gadget with id: " + id + " not found on page.");
        }

        AtlassianWebDriver.waitUntil(new VisibilityOfElementLocated(By.id(frameId)));
        this.chrome = driver.findElement(By.id(chromeId));
    }

    /**
     * Switches to the gadget iframe and returns a GadgetView
     * @return
     */
    public GadgetView view()
    {
        driver.switchTo().frame(frameId);
        AtlassianWebDriver.waitUntil(new VisibilityOfElementLocated(By.className("view")));

        return new GadgetView(driver.findElement(By.className("view")));
    }

    public void minimize()
    {

        WebElement dropdown = openDropdown();
        if (Check.elementExists(By.className("minimization"), dropdown))
        {
            dropdown.findElement(By.className("minimization")).click();
        }
        else
        {
            closeDropdown();
        }

    }

    public void maximize()
    {

        WebElement dropdown = openDropdown();
        if (Check.elementExists(By.className("maximization"), dropdown))
        {
            dropdown.findElement(By.className("maximization")).click();
        }
        else
        {
            closeDropdown();
        }

    }

    public void refresh()
    {

        WebElement dropdown = openDropdown();
        if (Check.elementExists(By.className("reload"), dropdown))
        {
            dropdown.findElement(By.cssSelector(".reload .no_target")).click();
        }
        else
        {
            closeDropdown();
        }

    }

    public String getGadgetTitle()
    {
        return AtlassianWebDriver.getDriver().findElement(By.id(titleId)).getText();
    }

    private WebElement openDropdown()
    {
        WebElement dropdown = getDropdown();

        if (Check.hasClass("hidden", dropdown))
        {
            MouseEvents.hover(chrome, driver)
                    .findElement(By.className(("aui-dd-trigger")))
                    .click();
        }

        return dropdown;
    }

    private void closeDropdown()
    {
        WebElement dropdown = getDropdown();

        if (!Check.hasClass("hidden", dropdown))
        {
            chrome.findElement(By.className("aui-dd-trigger")).click();
        }

    }

    private WebElement getDropdown()
    {
        return chrome.findElement(By.className("aui-dropdown"));
    }

}