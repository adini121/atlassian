package com.atlassian.webdriver.utils.table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Row implements WebElement
{
    private final WebElement row;
    private final List<Column> columns;

    public Row(WebElement el)
    {
        this.row = el;
        this.columns = new ArrayList<Column>();
    }

    public int numColumns()
    {
        initialiseColumns();
        return columns.size();
    }

    public Column getColumn(int index)
    {
        initialiseColumns();
        return columns.get(index);
    }

    public Iterator<Column> iterator()
    {
        initialiseColumns();
        return columns.iterator();
    }

    private void initialiseColumns()
    {

        if (columns.size() <= 0)
        {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            for (WebElement col : cols)
            {
                columns.add(new Column(col));
            }
        }

    }

    public void click()
    {
        row.click();
    }

    public void submit()
    {
        row.submit();
    }

    public String getValue()
    {
        return row.getValue();
    }

    public void sendKeys(final CharSequence... charSequences)
    {
        row.sendKeys(charSequences);
    }

    public void clear()
    {
        row.clear();
    }

    public String getTagName()
    {
        return row.getTagName();
    }

    public String getAttribute(final String s)
    {
        return row.getAttribute(s);
    }

    public boolean toggle()
    {
        return row.toggle();
    }

    public boolean isSelected()
    {
        return row.isSelected();
    }

    public void setSelected()
    {
        row.setSelected();
    }

    public boolean isEnabled()
    {
        return row.isEnabled();
    }

    public String getText()
    {
        return row.getText();
    }

    public List<WebElement> findElements(final By by)
    {
        return row.findElements(by);
    }

    public WebElement findElement(final By by)
    {
        return row.findElement(by);
    }
}