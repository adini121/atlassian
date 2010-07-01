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

    @Override
    public void click()
    {
        column.click();
    }

    @Override
    public void submit()
    {
        column.submit();
    }

    @Override
    public String getValue()
    {
        return column.getValue();
    }

    @Override
    public void sendKeys(final CharSequence... charSequences)
    {
        column.sendKeys(charSequences);
    }

    @Override
    public void clear()
    {
        column.clear();
    }

    @Override
    public String getTagName()
    {
        return column.getTagName();
    }

    @Override
    public String getAttribute(final String s)
    {
        return column.getAttribute(s);
    }

    @Override
    public boolean toggle()
    {
        return column.toggle();
    }

    @Override
    public boolean isSelected()
    {
        return column.isSelected();
    }

    @Override
    public void setSelected()
    {
        column.setSelected();
    }

    @Override
    public boolean isEnabled()
    {
        return column.isEnabled();
    }

    @Override
    public String getText()
    {
        return column.getText();
    }

    @Override
    public List<WebElement> findElements(final By by)
    {
        return column.findElements(by);
    }

    @Override
    public WebElement findElement(final By by)
    {
        return column.findElement(by);
    }


}