package com.atlassian.webdriver.component.jira.dashboard;

import com.atlassian.webdriver.AtlassianWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class GadgetView implements WebElement
{
    WebElement view;

    public GadgetView(WebElement view)
    {
        this.view = view;
    }

    public void close()
    {
        AtlassianWebDriver.getDriver().switchTo().defaultContent();
    }

    @Override
    public void click()
    {
        view.click();
    }

    @Override
    public void submit()
    {
        view.submit();
    }

    @Override
    public String getValue()
    {
        return view.getValue();
    }

    @Override
    public void sendKeys(final CharSequence... charSequences)
    {
        view.sendKeys();
    }

    @Override
    public void clear()
    {
        view.clear();
    }

    @Override
    public String getTagName()
    {
        return view.getTagName();
    }

    @Override
    public String getAttribute(final String s)
    {
        return view.getAttribute(s);
    }

    @Override
    public boolean toggle()
    {
        return view.toggle();
    }

    @Override
    public boolean isSelected()
    {
        return view.isSelected();
    }

    @Override
    public void setSelected()
    {
        view.setSelected();
    }

    @Override
    public boolean isEnabled()
    {
        return view.isEnabled();
    }

    @Override
    public String getText()
    {
        return view.getText();
    }

    @Override
    public List<WebElement> findElements(final By by)
    {
        return view.findElements(by);
    }

    @Override
    public WebElement findElement(final By by)
    {
        return view.findElement(by);
    }
}