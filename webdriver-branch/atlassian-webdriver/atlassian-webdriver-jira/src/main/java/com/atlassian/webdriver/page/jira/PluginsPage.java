package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.utils.table.Column;
import com.atlassian.webdriver.utils.table.Row;
import com.atlassian.webdriver.utils.table.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Page object implementation for the Plugins page for JIRA.
 */
public class PluginsPage extends JiraWebDriverPage
{
    private final static String URI = "/secure/admin/jira/ViewPlugins!default.jspa";

    private static final String PLUGIN_KEY = "pluginKey=";
    private final Map<String, WebElement> loadedPlugins;
    private String activePluginKey;
    private final Set<String> pluginsWithErrors;

    public PluginsPage(WebDriver driver)
    {
        super(driver);

        this.loadedPlugins = new HashMap<String, WebElement>();
        this.pluginsWithErrors = new HashSet<String>();
    }

    public PluginsPage get(boolean activated)
    {
        get(URI, activated);

        getLoadedPlugins();

        return this;
    }

    public boolean pluginIsLoaded(String pluginKey)
    {
        return loadedPlugins.containsKey(pluginKey);
    }

    public Set<String> getPluginsWithLoadingErrors()
    {
        return pluginsWithErrors;
    }

    public PluginsPage selectPlugin(String pluginKey)
    {
        if (pluginIsLoaded(pluginKey))
        {
            loadedPlugins.get(pluginKey).click();
            return JiraPage.PLUGINSPAGE.get(driver, true);
        }

        return null;
    }

    public String getActivePluginKey()
    {
        return activePluginKey;
    }

    /**
     * TODO: redo using jquery selectors
     */
    private void getLoadedPlugins()
    {
        // Match rows that have a column with an anchor tag
        Table pluginsTable = new Table(By.cssSelector("table table table table"), By.cssSelector("td a"), driver);

        for (int i = 0; i < pluginsTable.numRows(); i++)
        {
            Row row = pluginsTable.getRow(i);
            Column col = row.getColumn(row.numColumns() - 1);

            WebElement pluginLink = col.findElement(By.cssSelector("a"));
            String pluginUrl = pluginLink.getAttribute("href");
            int pluginKeyIndex = pluginUrl.indexOf(PLUGIN_KEY);
            String pluginKey = pluginUrl.substring(pluginKeyIndex + PLUGIN_KEY.length());

            if (col.getText().contains("Errors loading plugin"))
            {
                this.pluginsWithErrors.add(pluginKey);
            }

            if (row.numColumns() == 1)
            {
                this.activePluginKey = pluginKey;
            }

            loadedPlugins.put(pluginKey, pluginLink);
        }

    }

}