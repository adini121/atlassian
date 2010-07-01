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
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class PluginsPage extends JiraWebDriverPage
{
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

    public PluginsPage get()
    {
        if (!at("/secure/admin/jira/ViewPlugins!default.jspa"))
        {
            goTo("/secure/admin/jira/ViewPlugins!default.jspa");
        }

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
            return JiraPage.PLUGINS.get(driver);
        }

        return null;
    }

    public String getActivePluginKey()
    {
        return activePluginKey;
    }

    private void getLoadedPlugins()
    {
        // Match rows that only have two columns
        Table pluginsTable = new Table(By.cssSelector("table table table table"), driver, By.cssSelector("td a"));

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