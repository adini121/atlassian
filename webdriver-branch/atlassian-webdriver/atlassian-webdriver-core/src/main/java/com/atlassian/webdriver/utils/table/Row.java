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
            Iterator<WebElement> iter = cols.iterator();

            while (iter.hasNext())
            {
                columns.add(new Column(iter.next()));
            }
        }

    }

    @Override
    public void click()
    {
        row.click();
    }

    @Override
    public void submit()
    {
        row.submit();
    }

    @Override
    public String getValue()
    {
        return row.getValue();
    }

    @Override
    public void sendKeys(final CharSequence... charSequences)
    {
        row.sendKeys(charSequences);
    }

    @Override
    public void clear()
    {
        row.clear();
    }

    @Override
    public String getTagName()
    {
        return row.getTagName();
    }

    @Override
    public String getAttribute(final String s)
    {
        return row.getAttribute(s);
    }

    @Override
    public boolean toggle()
    {
        return row.toggle();
    }

    @Override
    public boolean isSelected()
    {
        return row.isSelected();
    }

    @Override
    public void setSelected()
    {
        row.setSelected();
    }

    @Override
    public boolean isEnabled()
    {
        return row.isEnabled();
    }

    @Override
    public String getText()
    {
        return row.getText();
    }

    @Override
    public List<WebElement> findElements(final By by)
    {
        return row.findElements(by);
    }

    @Override
    public WebElement findElement(final By by)
    {
        return row.findElement(by);
    }
}