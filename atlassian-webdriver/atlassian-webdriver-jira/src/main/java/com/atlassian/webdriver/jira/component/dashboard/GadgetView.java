package com.atlassian.webdriver.jira.component.dashboard;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * GadgetView is an implentation of the internal view for a gadget and just extends a
 * WebElement so all the normal methods that WebDriver exposes on web elements is available.
 */
public class GadgetView implements WebElement
{
    WebElement view;
    private final WebDriver driver;

    public GadgetView(WebElement view, JiraTestedProduct jiraTestedProduct)
    {
        this.driver = jiraTestedProduct.getDriver();
        this.view = view;
    }

    /**
     * Closes the Gadget view and returns the WebDriver context back to the default content
     * Which is usually the Dashboard content.
     */
    public void close()
    {
        driver.switchTo().defaultContent();
    }

    public void click()
    {
        view.click();
    }

    public void submit()
    {
        view.submit();
    }

    public String getValue()
    {
        return view.getValue();
    }

    public void sendKeys(final CharSequence... charSequences)
    {
        view.sendKeys();
    }

    public void clear()
    {
        view.clear();
    }

    public String getTagName()
    {
        return view.getTagName();
    }

    public String getAttribute(final String s)
    {
        return view.getAttribute(s);
    }

    public boolean toggle()
    {
        return view.toggle();
    }

    public boolean isSelected()
    {
        return view.isSelected();
    }

    public void setSelected()
    {
        view.setSelected();
    }

    public boolean isEnabled()
    {
        return view.isEnabled();
    }

    public String getText()
    {
        return view.getText();
    }

    public List<WebElement> findElements(final By by)
    {
        return view.findElements(by);
    }

    public WebElement findElement(final By by)
    {
        return view.findElement(by);
    }
}