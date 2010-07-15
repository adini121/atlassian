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
        this(at, null, driver);
    }

    public Table(By at, By rowMatcher, WebDriver driver)
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
            for(WebElement el : rows)
            {
                // Only add the row element if there is no rowMatch, or the
                // row matcher finds a match
                if (rowMatch == null || Check.elementExists(rowMatch, el))
                {
                    tableRows.add(new Row(el));
                }
            }
        }

    }

    public void click()
    {
        table.click();
    }

    public void submit()
    {
        table.submit();
    }

    public String getValue()
    {
        return table.getValue();
    }

    public void sendKeys(final CharSequence... charSequences)
    {
        table.sendKeys(charSequences);
    }

    public void clear()
    {
        table.clear();
    }

    public String getTagName()
    {
        return table.getTagName();
    }

    public String getAttribute(final String s)
    {
        return table.getAttribute(s);
    }

    public boolean toggle()
    {
        return table.toggle();
    }

    public boolean isSelected()
    {
        return table.isSelected();
    }

    public void setSelected()
    {
        table.setSelected();
    }

    public boolean isEnabled()
    {
        return table.isEnabled();
    }

    public String getText()
    {
        return table.getText();
    }

    public List<WebElement> findElements(final By by)
    {
        return table.findElements(by);
    }

    public WebElement findElement(final By by)
    {
        return table.findElement(by);
    }
}