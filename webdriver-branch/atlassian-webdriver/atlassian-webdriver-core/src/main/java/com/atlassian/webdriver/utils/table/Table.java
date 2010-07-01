package com.atlassian.webdriver.utils.table;

import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Table implements WebElement
{
    private final WebElement table;
    private final By rowMatch;

    private final List<Row> tableRows;
    private final List<Row> tableHeadings;

    public Table(By at, WebDriver driver)
    {
        this(at, driver, null);
    }

    public Table(By at, WebDriver driver, By rowMatcher)
    {
        this.table = driver.findElement(at);
        this.rowMatch = rowMatcher;
        this.tableRows = new ArrayList<Row>();
        this.tableHeadings = new ArrayList<Row>();
    }

    public int numRows()
    {
        initialiseRows();
        return tableRows.size();
    }

    public Row getRow(int index)
    {
        initialiseRows();
        return tableRows.get(index);
    }

    public Iterator<Row> iterator()
    {
        initialiseRows();
        return tableRows.iterator();
    }

    private void initialiseRows()
    {

        if (tableRows.size() <= 0)
        {
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            Iterator<WebElement> iter = rows.iterator();

            while (iter.hasNext())
            {
                WebElement el = iter.next();
                // Only add the row element if there is no rowMatch, or the
                // row matcher finds a match
                if (rowMatch == null || Check.elementExists(rowMatch, el))
                {
                    tableRows.add(new Row(el));
                }
            }
        }

    }

    @Override
    public void click()
    {
        table.click();
    }

    @Override
    public void submit()
    {
        table.submit();
    }

    @Override
    public String getValue()
    {
        return table.getValue();
    }

    @Override
    public void sendKeys(final CharSequence... charSequences)
    {
        table.sendKeys(charSequences);
    }

    @Override
    public void clear()
    {
        table.clear();
    }

    @Override
    public String getTagName()
    {
        return table.getTagName();
    }

    @Override
    public String getAttribute(final String s)
    {
        return table.getAttribute(s);
    }

    @Override
    public boolean toggle()
    {
        return table.toggle();
    }

    @Override
    public boolean isSelected()
    {
        return table.isSelected();
    }

    @Override
    public void setSelected()
    {
        table.setSelected();
    }

    @Override
    public boolean isEnabled()
    {
        return table.isEnabled();
    }

    @Override
    public String getText()
    {
        return table.getText();
    }

    @Override
    public List<WebElement> findElements(final By by)
    {
        return table.findElements(by);
    }

    @Override
    public WebElement findElement(final By by)
    {
        return table.findElement(by);
    }
}