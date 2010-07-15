package com.atlassian.webdriver.utils.table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Column implements WebElement
{
    private final WebElement column;

    public Column(WebElement el)
    {
        this.column = el;
    }

    public WebElement getElement()
    {
        return column;
    }

    public void click()
    {
        column.click();
    }

    public void submit()
    {
        column.submit();
    }

    public String getValue()
    {
        return column.getValue();
    }

    public void sendKeys(final CharSequence... charSequences)
    {
        column.sendKeys(charSequences);
    }

    public void clear()
    {
        column.clear();
    }

    public String getTagName()
    {
        return column.getTagName();
    }

    public String getAttribute(final String s)
    {
        return column.getAttribute(s);
    }

    public boolean toggle()
    {
        return column.toggle();
    }

    public boolean isSelected()
    {
        return column.isSelected();
    }

    public void setSelected()
    {
        column.setSelected();
    }

    public boolean isEnabled()
    {
        return column.isEnabled();
    }

    public String getText()
    {
        return column.getText();
    }

    public List<WebElement> findElements(final By by)
    {
        return column.findElements(by);
    }

    public WebElement findElement(final By by)
    {
        return column.findElement(by);
    }


}